(ns clj-cctray.parser
  "Main functions for parsing the CCTray XML."
  (:require [clojure.xml :as xml]
            [clj-cctray.health :refer :all]
            [clj-cctray.dates :refer :all]
            [clj-cctray.messages :refer :all]
            [clj-cctray.util :refer :all]))

(defn- is-project? [data]
  (= (:tag data) :Project))

(defn- to-map [source]
  (xml/parse source))

(defn- by-modifying-attributes [attributes thing]
  (merge attributes (thing attributes)))

(defn- extract-attributes [data modifiers]
  (if (is-project? data)
    (merge (reduce by-modifying-attributes
                   (keywordize-camel-keys (:attrs data))
                   (concat [keyword-activity keyword-status extract-dates add-prognosis] modifiers))
           (extract-messages data))))

(defn get-projects
  "Gets a list of projects from the given source converted into a nice clojure map. The source may be a File,
  InputStream or String naming a URI"
  ([source] (get-projects source []))
  ([source modifiers]
  (->> (:content (to-map source))
       (map #(extract-attributes % modifiers))
       (remove nil?))))
