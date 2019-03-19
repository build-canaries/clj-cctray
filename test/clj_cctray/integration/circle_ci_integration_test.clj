(ns clj-cctray.integration.circle-ci-integration-test
  (:require [clj-cctray.core :as subject]
            [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clj-time.core :as t]))

(def test-data-url "resources/circle_ci_example.xml")

(deftest circle-ci-example
  (testing "required test xml file exists"
    (is (true? (.exists (io/as-file test-data-url)))))

  (testing "will create list of projects"
    (is (= {:name              "example-service"
            :activity          :sleeping
            :prognosis         :healthy
            :last-build-status :success
            :last-build-label  "39"
            :last-build-time   (t/date-time 2016 7 23 10 34 33 757)
            :next-build-time   nil
            :web-url           "https://circleci.com/gh/owner/example-service/tree/branch"
            :owner             "owner"
            :messages          []}
           (first (subject/get-projects test-data-url {:server :circle}))))))
