(ns clj-cctray.util
  "Utility functions."
  (:require [clojure.string :refer [join lower-case split replace trim]])
  (:refer-clojure :exclude [join replace]))

(defn in?
  "Returns true if the given element is in the given sequence."
  [seq elm]
  (some #(= elm %) seq))

(defn- threading-join [coll separator]
  (join separator coll))

(defn keywordize-camel
  "Converts the given camel cased string into a clojure style keyword.

  For example: \"CamelCase\" => :camel-case"
  [camel-str]
  (if camel-str
    (->
      (name camel-str)
      (split #"(?<=[a-z])(?=[A-Z])")
      (threading-join "-")
      (lower-case)
      (keyword))))

(defn keywordize-camel-keys
  "Converts all the keys in the given map into keywords."
  [m]
  (into {} (map (fn [[k v]] [(keywordize-camel k) v]) m)))

(defn sentenceize
  "Sentenceizes the given string which means camel, snake, kebab and dot cased strings are turned into normal sentences
  with spaces. This function will not split the following:

  1. Multiple uppercased letters in a row
  2. Digits separated by dots

  This is in an attempt to keep acronyms/initialisms and version numbers from getting split.

  For example:

      \"CamelCased_SNAKE-kebab.dot_JSON-1.2.3\" => \"Camel Cased SNAKE kebab dot JSON 1.2.3\""
  [str]
  (if-not (nil? str)
    (-> str
        (replace #"[-_]+", " ")
        (replace #"\.(?!\d)|(?<!\d)\.", " ")
        (replace #"([a-z])([A-Z])", "$1 $2")
        trim)))

(defn normalise-string
  "Normalises the given string which means it is lower cased and sentenceized.

  For example:

      \"CamelCased_SNAKE-kebab.DoT\" => \"camel cased snake kebab dot\""
  [str]
  (if-not (nil? str)
    (lower-case (sentenceize str))))

(defn normalise-key
  "Normalises the given key in the given map which means it is lower cased and sentenceized.

  For example:

      \"CamelCased_SNAKE-kebab.DoT\" => \"camel cased snake kebab dot\""
  [key map]
  (if-let [value (get map key)]
    (assoc map key (normalise-string value))
    map))