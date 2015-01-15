(ns clj-cctray.dates
  "Functions for converting dates found in the cctray xml."
  (:require [clj-time.format :as f]
            [clj-time.core :as t]
            [clojure.string :refer [blank?]]))

(def iso-format "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")

(def ^:private xs-date-time-format-no-milli "yyyy-MM-dd'T'HH:mm:ssZ")
(def ^:private xs-date-time-format-no-milli-or-timezone "yyyy-MM-dd'T'HH:mm:ss")
(def ^:private xs-date-time-format "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
(def ^:private xs-date-time-format-no-timezone "yyyy-MM-dd'T'HH:mm:ss.SSS")

(def ^:private date-parser (f/formatter t/utc
                                        xs-date-time-format
                                        xs-date-time-format-no-milli
                                        xs-date-time-format-no-timezone
                                        xs-date-time-format-no-milli-or-timezone))

(defn parse-date
  "Converts a string in any of the known cctray xml date formats into a real DateTime object."
  [s]
  (if-not (blank? s) (f/parse date-parser s)))

(defn- print-date [formatter d]
  (if d (f/unparse formatter d)))

(defn extract-dates
  "Extracts the last and next build times from the cctray xml into real DateTime objects."
  [{:keys [last-build-time next-build-time]}]
  {:last-build-time (parse-date last-build-time)
   :next-build-time (parse-date next-build-time)})

(defn print-dates
  "Prints the last and next build times using the given string format, they must be DateTime objects to be printed successfully."
  [format {:keys [last-build-time next-build-time]}]
  (let [actual-format (if (blank? format) iso-format format)
        formatter (f/formatter actual-format)]
    {:last-build-time (print-date formatter last-build-time)
     :next-build-time (print-date formatter next-build-time)}))