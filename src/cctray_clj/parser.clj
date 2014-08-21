(ns cctray-clj.parser
  (:require [clojure.xml :as xml]))

(defn to-map [url]
  (xml/parse url))
