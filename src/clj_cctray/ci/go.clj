(ns clj-cctray.ci.go
  "Functions specific to the GoCD server."
  (:require [clojure.string :refer [split join]]
            [clj-cctray.util :refer :all]))

(defn- contains-job? [split-name]
  (> (count split-name) 2))

(defn split-name
  "GoCD combines the project name, stage and job into the CCTray XML name attribute, using :: as a delimiter.

  This function splits the name into individual entries in the project map.

  So instead of:

      {:name \"Project Name :: Stage Name :: Job Name\"}

  You end up with:

      {:name  \"Project Name\"
       :stage \"Stage Name\"
       :job   \"Job Name\"}"
  [{:keys [name]}]
  (let [split-name (split name #"\s::\s")]
    {:name  (first split-name)
     :stage (second split-name)
     :job   (if (contains-job? split-name)
              (last split-name))}))

(defn normalise-job
  "Normalises the job name in the given project map."
  [project]
  (normalise-key :job project))

(defn normalise-stage
  "Normalises the stage name in the given project map."
  [project]
  (normalise-key :stage project))
