(ns clj-cctray.ci.circle-ci
  "Functions specific to the CircleCI server."
  (:require [clojure.string :refer [trim]]
            [clj-cctray.util :refer :all]))

(defn split-name
  "CircleCI combines the owner and project name into the CCTray XML name attribute.

  This function splits the name into individual entries in the project map.

  So instead of:

      {:name \"Owner/Project Name\"}

  You end up with:

      {:name   \"Project Name\"
       :owner  \"Owner\"}"
  [{:keys [name]}]
  (let [matches (map trim (re-find #"^(.+?)/(.+?)$" name))]
    (if (= (count matches) 3)
      {:owner (nth matches 1)
       :name  (nth matches 2)})))
