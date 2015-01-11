(ns clj-cctray.integration.go-integration-test
  (:require [clj-cctray.core :as subject]
            [midje.sweet :refer :all]
            [clojure.java.io :as io]
            [clj-time.core :as t]))

(def test-data-url "resources/go_example.xml")

(fact "required test xml file exists"
      (.exists (io/as-file test-data-url)) => true)

(fact "will create list of projects"
      (subject/get-projects test-data-url {:server :go}) => (has every? (just {:name              anything
                                                                               :activity          keyword?
                                                                               :prognosis         keyword?
                                                                               :last-build-status keyword?
                                                                               :last-build-label  anything
                                                                               :last-build-time   anything
                                                                               :next-build-time   anything
                                                                               :web-url           anything
                                                                               :stage             anything
                                                                               :job               anything})))

