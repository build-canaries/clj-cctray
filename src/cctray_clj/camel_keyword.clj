(ns cctray-clj.camel-keyword
  (:require [clojure.string :refer [join lower-case split]])
  (:refer-clojure :exclude [join]))

(defn- threading-join [coll separator]
  (join separator coll))

(defn keywordize-camel [s]
  (->
    (name s)
    (split #"(?<=[a-z])(?=[A-Z])")
    (threading-join "-")
    (lower-case)
    (keyword)))

(defn keywordize-camel-keys [m]
  (into {} (map (fn [[k v]] [(keywordize-camel k) v]) m)))