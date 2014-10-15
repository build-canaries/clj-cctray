(ns cctray-clj.parser
  (:require [clojure.xml :as xml]
            [clojure.string :refer [split join]]
            [cctray-clj.name :refer :all]
            [cctray-clj.health :refer :all]
            [cctray-clj.camel-keyword :refer :all]))

(defn to-map [url]
  (xml/parse url))

(defn extract-attributes [data]
  (if (= (:tag data) :Project)
    (let [attributes (keywordize-camel-keys (:attrs data))]
      (merge
        attributes
        (extract-name (:name attributes))
        (extract-health attributes)))))

(defn get-projects [url]
  (->> (:content (to-map url))
       (map #(extract-attributes %))
       (remove nil?)))

(defn get-interesting-projects [url]
  (filter (fn [{:keys [prognosis]}] (not= prognosis :healthy)) (get-projects url)))
