(ns clj-cctray.ci.snap
  "Functions specific to the Snap CI server."
  (:require [clojure.string :refer [trim]]
            [clj-cctray.util :refer :all]))

(defn split-name
  "Snap combines the owner, project name, git branch and stage name into the cctray xml name attribute.

  This function splits the name into individual entries in the project map.

  So instead of:

      {:name \"Owner/Project Name (branch) :: Stage Name\"}

  You end up with:

      {:name   \"Project Name\"
       :stage  \"Stage Name\"
       :owner  \"Owner\"
       :branch \"branch\"}"
  [project]
  (let [matches (map trim (re-find #"^(.+?)/(.+?) \((.+?)\) :: (.*)$" (:name project)))]
    (merge project
           (if (= (count matches) 5)
             {:owner  (nth matches 1)
              :name   (nth matches 2)
              :branch (nth matches 3)
              :stage  (nth matches 4)}))))

(defn normalise-owner
  "Normalises the owner name in the given project map."
  [project]
  (assoc project :owner (normalise-string (:owner project))))

(defn normalise-branch
  "Normalises the branch name in the given project map."
  [project]
  (assoc project :branch (normalise-string (:branch project))))