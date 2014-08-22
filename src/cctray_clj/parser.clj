(ns cctray-clj.parser
  (:require [clojure.xml :as xml]))

(defn to-map [url]
  (xml/parse url))

(defn get-projects [url]
  (let [content (:content (to-map url))]
    (remove nil? (map #(if (= (:tag %) :Project) (:attrs %)) content))))
