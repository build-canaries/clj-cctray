(ns clj-cctray.integration.jenkins-integration-test
  (:require [clj-cctray.core :as subject]
            [clojure.test :refer :all]
            [clojure.java.io :as io]))

(def test-data-url "resources/jenkins_example.xml")

(deftest jenkins-example
  (testing "required test xml file exists"
    (is (true? (.exists (io/as-file test-data-url)))))

  (testing "will create list of projects"
    (is (= {:name              "example-service-build"
            :activity          :sleeping
            :prognosis         :healthy
            :last-build-status :success
            :last-build-label  "429"
            :last-build-time   "2015-01-16T14:54:59.000Z"
            :next-build-time   ""
            :web-url           "https://host/view/Example-Service-Pipeline/job/example-service-build/"
            :messages          []}
           (first (subject/get-projects test-data-url {:server :jenkins :print-dates true}))))))
