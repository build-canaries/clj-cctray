(ns clj-cctray.integration.go-integration-test
  (:require [clj-cctray.core :as subject]
            [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clj-time.core :as t]))

(def test-data-url "resources/go_example.xml")

(deftest go-example
  (testing "required test xml file exists"
    (is (true? (.exists (io/as-file test-data-url)))))

  (testing "will create list of projects"
    (is (= {:name              "example-service"
            :stage             "test"
            :job               nil
            :activity          :sleeping
            :prognosis         :healthy
            :last-build-status :success
            :last-build-label  "648"
            :last-build-time   (t/date-time 2014 10 7 12 51 38)
            :next-build-time   nil
            :web-url           "http://host:8153/go/pipelines/example-service/648/test/1"
            :messages          []}
           (first (subject/get-projects test-data-url {:server :go}))))))
