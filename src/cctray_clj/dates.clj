(ns cctray-clj.dates
  (:require [clj-time.format :as f]
            [clj-time.core :as t]))

(def xs-date-time-format-no-milli "yyyy-MM-dd'T'HH:mm:ssZ")
(def xs-date-time-format-no-milli-or-timezone "yyyy-MM-dd'T'HH:mm:ss")
(def xs-date-time-format "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
(def xs-date-time-format-no-timezone "yyyy-MM-dd'T'HH:mm:ss.SSS")

(def date-parser (f/formatter t/utc
                              xs-date-time-format
                              xs-date-time-format-no-milli
                              xs-date-time-format-no-timezone
                              xs-date-time-format-no-milli-or-timezone))

(defn extract-dates [{:keys [last-build-time]}]
  {:last-build-time (f/parse date-parser last-build-time)})
