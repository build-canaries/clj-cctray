(ns cctray-clj.parser
  (:require [clojure.xml :as xml]
            [clojure.string :refer [split join]]
            [cctray-clj.name :refer :all]
            [cctray-clj.health :refer :all]))

(defn to-map [url]
  (xml/parse url))

(defn extract-attributes [data]
  (if (= (:tag data) :Project)
    (merge
      (:attrs data)
      (extract-name (get-in data [:attrs :name]))
      (extract-health (get-in data [:attrs])))))

(defn get-projects [url]
  (->> (:content (to-map url))
       (map #(extract-attributes %))
       (remove nil?)))

(defn get-interesting-projects [url]
  (filter (fn [{:keys [prognosis]}] (not= prognosis "healthy")) (get-projects url)))