(ns cctray-clj.name
  (:require [clojure.string :refer [split join]]))

(defn sentanceize [input-string]
  (-> input-string
      (clojure.string/replace #"[-_]+", " ")
      (clojure.string/replace #"([a-z])([A-Z])", "$1 $2")))

(defn extract-name [name]
  (let [split-name (split name #"\s::\s")]
    {:name     (sentanceize (first split-name))
     :raw-name (first split-name)
     :stage    (second split-name)
     :job      (if (> (count split-name) 1)
                 (last split-name))}))
