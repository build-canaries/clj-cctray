(ns clj-cctray.util
  "Utility functions."
  (:require [clojure.string :refer [join lower-case split]])
  (:refer-clojure :exclude [join]))

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