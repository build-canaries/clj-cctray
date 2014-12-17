(ns clj-cctray.parser
  "Main functions for parsing the cctray xml."
  (:require [clojure.xml :as xml]
            [clojure.string :refer [split join]]
            [clj-cctray.reader :refer :all]
            [clj-cctray.health :refer :all]
            [clj-cctray.dates :refer :all]
            [clj-cctray.camel-keyword :refer :all]))

(defn to-map
  "Converts the cctray xml at the given url into an ugly map matching the structure of the xml."
  [url]
  (xml/parse (xml-reader url)))

(defn- by-modifying-attributes [attributes fn]
  (merge attributes (fn attributes)))

(defn- extract-attributes [data]
  (if (= (:tag data) :Project)
    (reduce by-modifying-attributes
            (keywordize-camel-keys (:attrs data))
            [keyword-activity keyword-status extract-dates add-prognosis])))

(defn get-projects
  "Gets a list of projects from the given url converted into a nice clojure map."
  [url]
  (->> (:content (to-map url))
       (map #(extract-attributes %))
       (remove nil?)))
