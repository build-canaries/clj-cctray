(ns clj-cctray.dates-test
  (:require [clj-cctray.dates :as subject]
            [midje.sweet :refer :all]
            [clj-time.core :as t]))

(facts "parsing dates"
       (fact "no timezone default to utc"
             (subject/parse-date "2014-10-07T12:51:38") => (t/date-time 2014 10 7 12 51 38))

       (fact "with timezone"
             (subject/parse-date "2014-10-07T12:51:38z") => (t/date-time 2014 10 7 12 51 38)
             (subject/parse-date "2014-10-07T12:51:38+01:00") => (t/date-time 2014 10 7 11 51 38))

       (fact "with milliseconds and timezone"
             (subject/parse-date "2014-10-07T12:51:38.123z") => (t/date-time 2014 10 7 12 51 38 123)
             (subject/parse-date "2014-10-07T12:51:38.123-01:00") => (t/date-time 2014 10 7 13 51 38 123))

       (fact "with milliseconds and no timezone"
             (subject/parse-date "2014-10-07T12:51:38.123") => (t/date-time 2014 10 7 12 51 38 123))

       (fact "nil"
             (subject/parse-date nil) => nil)

       (fact "empty"
             (subject/parse-date "") => nil))

(facts "extracting dates"
       (background
         (subject/parse-date nil) => nil)

       (fact "last build time"
             (subject/extract-dates {:last-build-time ..last..}) => (contains {:last-build-time ..parsed-last..})
             (provided
               (subject/parse-date ..last..) => ..parsed-last..))

       (fact "next build time"
             (subject/extract-dates {:next-build-time ..next..}) => (contains {:next-build-time ..parsed-next..})
             (provided
               (subject/parse-date ..next..) => ..parsed-next..)))

(facts "print dates"
       (fact "defaults to iso format if format is nil"
             (subject/print-dates nil {:last-build-time (t/date-time 2015 1 14 22 20 38)}) => (contains {:last-build-time "2015-01-14T22:20:38.000+00:00"}))

       (fact "defaults to iso format if format is blank"
             (subject/print-dates "" {:last-build-time (t/date-time 2015 1 14 22 20 38)}) => (contains {:last-build-time "2015-01-14T22:20:38.000+00:00"}))

       (fact "last build time"
             (subject/print-dates "yyyy-MM-dd" {:last-build-time (t/date-time 2015 1 14 22 20 38)}) => (contains {:last-build-time "2015-01-14"}))

       (fact "handles nil last build time"
             (subject/print-dates "HH:mm:ss.SSSZZ" {}) => (contains {:last-build-time nil}))

       (fact "next build time"
             (subject/print-dates "HH:mm:ss.SSSZZ" {:next-build-time (t/date-time 2015 1 14 22 27 38)}) => (contains {:next-build-time "22:27:38.000+00:00"}))

       (fact "handles nil next build time"
             (subject/print-dates "HH:mm:ss.SSSZZ" {}) => (contains {:next-build-time nil})))