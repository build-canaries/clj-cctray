(ns clj-cctray.parser
  "Main functions for parsing the cctray xml."
  (:require [clojure.xml :as xml]
            [clj-cctray.reader :refer :all]
            [clj-cctray.health :refer :all]
            [clj-cctray.dates :refer :all]
            [clj-cctray.messages :refer :all]
            [clj-cctray.util :refer :all]))

(defn- is-project? [data]
  (= (:tag data) :Project))

(defn- to-map [url]
  (xml/parse (xml-reader url)))

(defn- by-modifying-attributes [attributes fn]
  (merge attributes (fn attributes)))

(defn- extract-attributes [data]
  (if (is-project? data)
    (merge (reduce by-modifying-attributes
                   (keywordize-camel-keys (:attrs data))
                   [keyword-activity keyword-status extract-dates add-prognosis])
           (extract-messsages data))))

(defn get-projects
  "Gets a list of projects from the given url converted into a nice clojure map."
  [url]
  (->> (:content (to-map url))
       (map #(extract-attributes %))
       (remove nil?)))
