(ns clj-cctray.parser
  (:require [clojure.xml :as xml]
            [clojure.string :refer [split join]]
            [clj-cctray.name :refer :all]
            [clj-cctray.health :refer :all]
            [clj-cctray.dates :refer :all]
            [clj-cctray.camel-keyword :refer :all]))

(defn to-map [url]
  (xml/parse url))

(defn- by-modifying-attributes [attributes fn]
  (merge attributes (fn attributes)))

(defn extract-attributes [data]
  (if (= (:tag data) :Project)
    (reduce by-modifying-attributes
            (keywordize-camel-keys (:attrs data))
            [keyword-activity keyword-status extract-dates add-prognosis extract-name])))

(defn get-projects [url]
  (->> (:content (to-map url))
       (map #(extract-attributes %))
       (remove nil?)))
