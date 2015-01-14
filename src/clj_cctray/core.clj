(ns clj-cctray.core
  "Contains the core public function to get parsed projects."
  (:require [clj-cctray.parser :as parser]
            [clj-cctray.name :as name]
            [clj-cctray.ci.thoughtworks-ci :as tw]
            [clj-cctray.ci.go :as go]
            [clj-cctray.ci.snap :as snap]
            [clj-cctray.util :refer :all]))

(defn- apply-processors [processors thing]
  (reduce #(%2 %1) thing processors))

(defn- normalise-partial [k]
  (partial normalise-key k))

(defn- project-modifiers-mappings [[option value]]
  (cond
    (and (= :server option) (= :go value)) go/split-name
    (and (= :server option) (= :snap value)) snap/split-name
    (and (= :normalise option) (coll? value)) (map #(normalise-partial %) value)
    (and (= :normalise option) value) [name/normalise-name, tw/normalise-stage, go/normalise-job snap/normalise-owner]))

(defn- post-processors-mappings [[option value]]
  (cond
    (and (= :server option) (tw/thoughtworks-server? value)) tw/distinct-projects))

(defn- parse-options [options processor-mappings]
  (remove nil? (flatten (map #(processor-mappings %) options))))

(defn- ^:testable project-modifiers [options]
  (parse-options options project-modifiers-mappings))

(defn- post-processors [options]
  (parse-options options post-processors-mappings))

(def ^:private default-options {})

(defn get-projects
  "Gets and parses the cctray xml file at the given source and returns a list of project maps.

  An optional map of options can be given to modify how the file is parsed.

  See the project README at https://github.com/build-canaries/clj-cctray/blob/master/README.md for more details
  about available options and the keys in each project map."
  ([source] (get-projects source {}))
  ([source user-supplied-options]
    (let [options (merge default-options user-supplied-options)]
      (->>
        (parser/get-projects source (project-modifiers options))
        (apply-processors (post-processors options))))))
