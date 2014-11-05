(ns cctray-clj.parser
  (:require [clojure.xml :as xml]
            [clojure.string :refer [split join]]
            [cctray-clj.name :refer :all]
            [cctray-clj.health :refer :all]
            [cctray-clj.dates :refer :all]
            [cctray-clj.camel-keyword :refer :all]))

(defn to-map [url]
  (xml/parse url))

(defn- update-attributes [attributes]
  (merge
    attributes
    (keyword-activity attributes)
    (keyword-status attributes)
    (extract-name attributes)
    (extract-dates attributes)))

(defn extract-attributes [data]
  (if (= (:tag data) :Project)
    (let [attributes (keywordize-camel-keys (:attrs data))
          updated-attributes (update-attributes attributes)]
      (merge updated-attributes
             (add-prognosis updated-attributes)))))

(defn get-projects [url]
  (->> (:content (to-map url))
       (map #(extract-attributes %))
       (remove nil?)))
