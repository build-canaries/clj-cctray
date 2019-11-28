(ns clj-cctray.ci.concourse
  "Functions specific to the Concourse server."
  (:require [clojure.string :refer [trim]]
            [clj-cctray.util :refer :all]))

(defn split-name
  "Concourse combines the pipeline and job name into the CCTray XML name attribute.

  This function splits the name into individual entries in the project map.

  So instead of:

      {:name \"Pipeline Name/Job Name\"}

  You end up with:

      {:name \"Pipeline Name\"
       :job  \"Job Name\"}"
  [{:keys [name]}]
  (let [matches (map trim (re-find #"^(.+?)/(.+?)$" name))]
    (if (= (count matches) 3)
      {:name (nth matches 1)
       :job  (nth matches 2)})))
