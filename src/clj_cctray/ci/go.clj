(ns clj-cctray.ci.go
  "Functions specific to the Go CI server."
  (:require [clojure.string :refer [split join]]
            [clj-cctray.name :refer :all]))

(defn- contains-job? [split-name]
  (> (count split-name) 2))

(defn split-name
  "Go combines the project name, stage and job into the cctray xml name attribute, using :: as a delimter.

  This function splits the name into individual entries in the project map.

  So instead of:

      {:name \"Project Name :: Stage Name :: Job Name\"}

  You end up with:

      {:name  \"Project Name\"
       :stage \"Stage Name\"
       :job   \"Job Name\"}"
  [project]
  (let [split-name (split (:name project) #"\s::\s")]
    (merge project {:name  (first split-name)
                    :stage (second split-name)
                    :job   (if (contains-job? split-name)
                             (last split-name))})))

(defn normalise-job
  "Normalises the job name in the given project map."
  [project]
  (assoc project :job (normalise-string (:job project))))
