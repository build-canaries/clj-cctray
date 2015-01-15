(ns clj-cctray.dates-test
  (:require [clj-cctray.dates :as subject]
            [midje.sweet :refer :all]
            [midje.util :refer [expose-testables]]
            [clj-time.core :as t]
            [clj-time.format :as f]))

(expose-testables clj-cctray.dates)

(facts "parsing dates"
       (fact "no timezone default to utc"
             (parse-date "2014-10-07T12:51:38") => (t/date-time 2014 10 7 12 51 38))

       (fact "with timezone"
             (parse-date "2014-10-07T12:51:38z") => (t/date-time 2014 10 7 12 51 38)
             (parse-date "2014-10-07T12:51:38+01:00") => (t/date-time 2014 10 7 11 51 38))

       (fact "with milliseconds and timezone"
             (parse-date "2014-10-07T12:51:38.123z") => (t/date-time 2014 10 7 12 51 38 123)
             (parse-date "2014-10-07T12:51:38.123-01:00") => (t/date-time 2014 10 7 13 51 38 123))

       (fact "with milliseconds and no timezone"
             (parse-date "2014-10-07T12:51:38.123") => (t/date-time 2014 10 7 12 51 38 123))

       (fact "nil"
             (parse-date nil) => nil)

       (fact "empty"
             (parse-date "") => nil))

(facts "print date"
       (let [formatter (f/formatter "yyyy-MM-dd")]

         (fact "nil date prints an empty string"
               (print-date formatter nil) => "")

         (fact "non DateTime objects get to-stringed"
               (print-date formatter []) => "[]")

         (fact "prints a DateTime"
               (print-date formatter (t/date-time 2015 1 14 22 20 38)) => "2015-01-14")))

(facts "extracting dates"
       (background
         (#'clj-cctray.dates/parse-date nil) => nil)

       (fact "last build time"
             (subject/extract-dates {:last-build-time ..last..}) => (contains {:last-build-time ..parsed-last..})
             (provided
               (#'clj-cctray.dates/parse-date ..last..) => ..parsed-last..))

       (fact "next build time"
             (subject/extract-dates {:next-build-time ..next..}) => (contains {:next-build-time ..parsed-next..})
             (provided
               (#'clj-cctray.dates/parse-date ..next..) => ..parsed-next..)))

(facts "print dates"
       (background
         (#'clj-cctray.dates/print-date anything nil) => nil)

       (fact "last build time"
             (subject/print-dates "yyyy-MM-dd" {:last-build-time ..last..}) => (contains {:last-build-time ..printed-last..})
             (provided
               (#'clj-cctray.dates/print-date anything ..last..) => ..printed-last..))

       (fact "next build time"
             (subject/print-dates "HH:mm:ss.SSSZZ" {:next-build-time ..next..}) => (contains {:next-build-time ..printed-next..})
             (provided
               (#'clj-cctray.dates/print-date anything ..next..) => ..printed-next..)))