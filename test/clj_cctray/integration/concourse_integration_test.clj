(ns clj-cctray.integration.concourse-integration-test
  (:require [clj-cctray.core :as subject]
            [clojure.test :refer :all]
            [clojure.java.io :as io]))

(def test-data-url "resources/concourse_example.xml")

(deftest concourse-example
  (testing "required test xml file exists"
    (is (true? (.exists (io/as-file test-data-url)))))

  (testing "will create list of projects"
    (is (= {:name              "hello-world"
            :job               "job-hello-world"
            :activity          :sleeping
            :prognosis         :healthy
            :last-build-status :success
            :last-build-label  "6"
            :last-build-time   "2019-05-17T16:04:30.000Z"
            :next-build-time   ""
            :web-url           "http://host/teams/main/pipelines/hello-world/jobs/job-hello-world"
            :messages          []}
           (first (subject/get-projects test-data-url {:server :concourse :print-dates true}))))))
