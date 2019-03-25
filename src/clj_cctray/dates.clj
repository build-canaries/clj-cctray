(ns clj-cctray.dates
  "Functions for converting dates found in the CCTray XML."
  (:require [java-time.format :as f]
            [java-time.temporal :as t]
            [clojure.string :refer [blank?]])
  (:import (java.time ZoneId)))

(defn- formatter [format]
  (.withZone (f/formatter format) (ZoneId/of "Z")))

(def iso-format
  "Format string in the ISO 8601 format, yyyy-MM-dd'T'HH:mm:ss.SSSVV"
  "yyyy-MM-dd'T'HH:mm:ss.SSSVV")

(def ^:private date-parser (formatter "yyyy-MM-dd'T'HH:mm:ss[.SSS][VV]"))

(defn parse-date [s]
  (if-not (blank? s)
    (try
      (t/instant date-parser s)
      (catch Exception _ nil))))

(defn print-date [format date]
  (if (t/instant? date)
    (f/format (formatter format) date)
    (str date)))

(defn extract-dates
  "Extracts the last and next build times from the CCTray XML into real Instant objects."
  [{:keys [last-build-time next-build-time]}]
  {:last-build-time (parse-date last-build-time)
   :next-build-time (parse-date next-build-time)})

(defn print-dates
  "Prints the last and next build times using the given string format. They must be Instant objects to be printed
  correctly using the format string, if any other object is found it will just be converted to a string using `str`"
  [format {:keys [last-build-time next-build-time]}]
  {:last-build-time (print-date format last-build-time)
   :next-build-time (print-date format next-build-time)})
