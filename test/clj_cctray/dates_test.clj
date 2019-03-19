(ns clj-cctray.dates-test
  (:require [clj-cctray.dates :as subject]
            [clojure.test :refer :all]
            [clj-time.core :as t]
            [clj-time.format :as f]))

(deftest parse-date
  (testing "no timezone default to utc"
    (is (= (t/date-time 2014 10 7 12 51 38) (subject/parse-date "2014-10-07T12:51:38"))))

  (testing "with timezone"
    (is (= (t/date-time 2014 10 7 12 51 38) (subject/parse-date "2014-10-07T12:51:38z")))
    (is (= (t/date-time 2014 10 7 11 51 38) (subject/parse-date "2014-10-07T12:51:38+01:00"))))

  (testing "with milliseconds and timezone"
    (is (= (t/date-time 2014 10 7 12 51 38 123) (subject/parse-date "2014-10-07T12:51:38.123z")))
    (is (= (t/date-time 2014 10 7 13 51 38 123) (subject/parse-date "2014-10-07T12:51:38.123-01:00"))))

  (testing "with milliseconds and no timezone"
    (is (= (t/date-time 2014 10 7 12 51 38 123) (subject/parse-date "2014-10-07T12:51:38.123"))))

  (testing "handles nil"
    (is (nil? (subject/parse-date nil))))

  (testing "handles an empty string"
    (is (nil? (subject/parse-date ""))))

  (testing "invalid date strings"
    (is (nil? (subject/parse-date "not-a-real-date-string")))))

(deftest print-date
  (let [formatter (f/formatter "yyyy-MM-dd")]

    (testing "nil date prints an empty string"
      (is (= "" (subject/print-date formatter nil))))

    (testing "non DateTime objects get to-stringed"
      (is (= "[]" (subject/print-date formatter []))))

    (testing "prints a DateTime"
      (is (= "2015-01-14" (subject/print-date formatter (t/date-time 2015 1 14 22 20 38)))))))

(deftest extract-dates
  (is (= {:last-build-time (t/date-time 2019 3 15 0 0 0)
          :next-build-time (t/date-time 2019 4 16 0 0 0)}
         (subject/extract-dates {:last-build-time "2019-03-15T00:00:00Z"
                                 :next-build-time "2019-04-16T00:00:00Z"}))))

(deftest print-dates
  (is (= {:last-build-time "2019-03-15"
          :next-build-time "2019-04-16"}
         (subject/print-dates "yyyy-MM-dd" {:last-build-time (t/date-time 2019 3 15 0 0 0)
                                            :next-build-time (t/date-time 2019 4 16 0 0 0)}))))
