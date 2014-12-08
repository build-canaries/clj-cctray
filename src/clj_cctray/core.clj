(ns clj-cctray.core
  (:require [clj-cctray.parser :as parser]
            [clj-cctray.name :as name]
            [clj-cctray.ci.go-snap :as snap]
            [clj-cctray.reader :as reader]
            [clj-cctray.util :refer :all]))

(defn- cruise-family [value]
  #(or (= value :go)
       (= value :snap)))

(defn- apply-processors [processors thing]
  (reduce #(%2 %1) thing processors))

(defn project-processors-mappings [[option value]]
  (cond
    (and (= :server option) (cruise-family value)) snap/extract-name
    (and (= :normalise option) (= :name value)) name/normalise-name
    (and (= :normalise option) (= :stage value)) snap/normalise-stage
    (and (= :normalise option) (= :job value)) snap/normalise-job
    (and (= :normalise option) (= :all value)) [name/normalise-name, snap/normalise-stage, snap/normalise-job]))

(defn pre-processors-mappings [[option value]])

(defn post-processors-mappings [[option value]]
  (cond
    (and (= :server option) (cruise-family value)) snap/distinct-projects))

(defn- parse-options [options processor-mappings]
  (remove nil? (flatten (map #(processor-mappings %) options))))

(defn project-processors [options]
  (parse-options options project-processors-mappings))

(defn pre-processors [options]
  (parse-options options pre-processors-mappings))

(defn post-processors [options]
  (parse-options options post-processors-mappings))

(def ^:private default-options {:strict-certificate-checks (not reader/http-kit-insecure?)
                                :http-timeout-seconds      reader/http-kit-timeout})

(defn get-projects [url & {:keys [options]
                           :or   {options default-options}}]
  (binding [reader/http-kit-insecure? (not (:strict-certificate-checks options))
            reader/http-kit-timeout (:http-timeout-seconds options)]
    (->>
      (parser/get-projects url)
      (apply-processors (pre-processors options))
      (map (partial apply-processors (project-processors options)))
      (apply-processors (post-processors options)))))
