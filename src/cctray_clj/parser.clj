(ns cctray-clj.parser
  (:require [clojure.xml :as xml]
            [clojure.string :refer [split]]))

(defn to-map [url]
  (xml/parse url))

(defn get-projects [url]
  (let [content (:content (to-map url))]
    (remove nil? (map
                   #(if (= (:tag %) :Project)
                     (:attrs %))
                   content))))

(defn extract-name [name]
  (let [split-name (split name #"\s::\s")]
    {:name (first split-name) :pipeline (second split-name) :stage (last split-name)}))