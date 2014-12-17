(ns clj-cctray.name
  "Functions for normalising and converting names."
  (:require [clojure.string :refer [replace lower-case]])
  (:refer-clojure :exclude [replace]))

(defn sentenceize
  "Sentenceizes the given string which means camel, snake and kebab cased strings are turned into normal sentences
  with spaces.

  For example:

      \"Camel_SNAKE-kebab\" => \"Camel SNAKE kebab\""
  [str]
  (if-not (nil? str)
    (-> str
        (replace #"[-_]+", " ")
        (replace #"([a-z])([A-Z])", "$1 $2"))))

(defn normalise-string
  "Normalises the given string which means it is lower cased and sentenceized.

  For example:

      \"Camel_SNAKE-kebab\" => \"camel snake kebab\""
  [str]
  (if-not (nil? str)
    (lower-case (sentenceize str))))

(defn normalise-name
  "Normalise the project name in the given project map."
  [project]
  (assoc project :name (normalise-string (:name project))))
