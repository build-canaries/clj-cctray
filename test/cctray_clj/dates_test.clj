(ns cctray-clj.dates-test
  (:require [cctray-clj.dates :as subject]
            [midje.sweet :refer :all]
            [clj-time.core :as t]))

(facts "last build time"
       (fact "no timezone default to utc"
             (subject/extract-dates {:last-build-time "2014-10-07T12:51:38"}) => {:last-build-time (t/date-time 2014 10 7 12 51 38)})

       (fact "with timezone"
             (subject/extract-dates {:last-build-time "2014-10-07T12:51:38z"}) => {:last-build-time (t/date-time 2014 10 7 12 51 38)}
             (subject/extract-dates {:last-build-time "2014-10-07T12:51:38+01:00"}) => {:last-build-time (t/date-time 2014 10 7 11 51 38)})

       (fact "with milliseconds and timezone"
             (subject/extract-dates {:last-build-time "2014-10-07T12:51:38.123z"}) => {:last-build-time (t/date-time 2014 10 7 12 51 38 123)}
             (subject/extract-dates {:last-build-time "2014-10-07T12:51:38.123-01:00"}) => {:last-build-time (t/date-time 2014 10 7 13 51 38 123)})

       (fact "with milliseconds and no timezone"
             (subject/extract-dates {:last-build-time "2014-10-07T12:51:38.123"}) => {:last-build-time (t/date-time 2014 10 7 12 51 38 123)}))