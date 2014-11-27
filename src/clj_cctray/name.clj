(ns clj-cctray.name
  (:require [clojure.string :refer [replace lower-case]])
  (:refer-clojure :exclude [replace]))

(defn sentenceize [str]
  (if-not (nil? str)
    (-> str
        (replace #"[-_]+", " ")
        (replace #"([a-z])([A-Z])", "$1 $2"))))

(defn normalise-string [str]
  (if-not (nil? str)
    (lower-case (sentenceize str))))

(defn normalise-name [project]
  (assoc project :name (normalise-string (:name project))))
