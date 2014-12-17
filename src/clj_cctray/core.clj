(ns clj-cctray.core
  "Contains the core public function to get parsed projects."
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

(defn- project-processors-mappings [[option value]]
  (cond
    (and (= :server option) (cruise-family value)) snap/extract-name
    (and (= :normalise option) (= :name value)) name/normalise-name
    (and (= :normalise option) (= :stage value)) snap/normalise-stage
    (and (= :normalise option) (= :job value)) snap/normalise-job
    (and (= :normalise option) (= :all value)) [name/normalise-name, snap/normalise-stage, snap/normalise-job]))

(defn- pre-processors-mappings [[option value]])

(defn- post-processors-mappings [[option value]]
  (cond
    (and (= :server option) (cruise-family value)) snap/distinct-projects))

(defn- parse-options [options processor-mappings]
  (remove nil? (flatten (map #(processor-mappings %) options))))

(defn- project-processors [options]
  (parse-options options project-processors-mappings))

(defn- pre-processors [options]
  (parse-options options pre-processors-mappings))

(defn- post-processors [options]
  (parse-options options post-processors-mappings))

(def ^:private default-options {:strict-certificate-checks (not reader/http-client-insecure?)
                                :http-timeout-seconds      reader/http-client-timeout})

(defn get-projects
  "Gets and parses the cctray xml file at the given url and returns a list of project maps.

  An optional map of options can be given to modify how the file is parsed.

  See the project README (https://github.com/build-canaries/clj-cctray/blob/master/README.md) for more details
  about available options and the keys in each project map."
  ([url] (get-projects url {}))
  ([url user-supplied-options]
    (let [options (merge default-options user-supplied-options)]
      (binding [reader/http-client-insecure? (not (:strict-certificate-checks options))
                reader/http-client-timeout (:http-timeout-seconds options)]
        (->>
          (parser/get-projects url)
          (apply-processors (pre-processors options))
          (map (partial apply-processors (project-processors options)))
          (apply-processors (post-processors options)))))))
