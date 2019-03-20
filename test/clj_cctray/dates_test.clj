(ns clj-cctray.dates-test
  (:require [clj-cctray.dates :as subject]
            [clojure.test :refer :all]
            [clj-cctray.dates :refer [iso-format]]
            [java-time :as t]))

(deftest parse-date
  (testing "no timezone should default to UTC"
    (is (= (t/instant "2014-10-07T12:51:38Z") (subject/parse-date "2014-10-07T12:51:38"))))

  (testing "with timezone"
    (is (= (t/instant "2014-10-07T12:51:38Z") (subject/parse-date "2014-10-07T12:51:38Z")))
    (is (= (t/instant "2014-10-07T11:51:38Z") (subject/parse-date "2014-10-07T12:51:38+01:00"))))

  (testing "with milliseconds and timezone"
    (is (= (t/instant (t/formatter iso-format) "2014-10-07T12:51:38.123Z") (subject/parse-date "2014-10-07T12:51:38.123Z")))
    (is (= (t/instant (t/formatter iso-format) "2014-10-07T13:51:38.123Z") (subject/parse-date "2014-10-07T12:51:38.123-01:00"))))

  (testing "with milliseconds and no timezone should default to UTC"
    (is (= (t/instant (t/formatter iso-format) "2014-10-07T12:51:38.123Z") (subject/parse-date "2014-10-07T12:51:38.123"))))

  (testing "handles nil"
    (is (nil? (subject/parse-date nil))))

  (testing "handles an empty string"
    (is (nil? (subject/parse-date ""))))

  (testing "invalid date strings"
    (is (nil? (subject/parse-date "not-a-real-date-string")))))

(deftest print-date
  (let [format "yyyy-MM-dd"]

    (testing "nil date prints an empty string"
      (is (= "" (subject/print-date format nil))))

    (testing "non Instant objects get to-stringed"
      (is (= "[]" (subject/print-date format []))))

    (testing "prints a Instant"
      (is (= "2015-01-14" (subject/print-date format (t/instant "2015-01-14T22:20:38Z")))))))

(deftest extract-dates
  (is (= {:last-build-time (t/instant "2019-03-15T00:00:00Z")
          :next-build-time (t/instant "2019-04-16T00:00:00Z")}
         (subject/extract-dates {:last-build-time "2019-03-15T00:00:00Z"
                                 :next-build-time "2019-04-16T00:00:00Z"}))))

(deftest print-dates
  (is (= {:last-build-time "2019-03-15"
          :next-build-time "2019-04-16"}
         (subject/print-dates "yyyy-MM-dd" {:last-build-time (t/instant "2019-03-15T00:00:00Z")
                                            :next-build-time (t/instant "2019-04-16T00:00:00Z")}))))
