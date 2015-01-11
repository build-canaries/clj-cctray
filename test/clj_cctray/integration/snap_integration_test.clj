(ns clj-cctray.integration.snap-integration-test
  (:require [clj-cctray.core :as subject]
            [midje.sweet :refer :all]
            [clojure.java.io :as io]
            [clj-time.core :as t]))

(def test-data-url "resources/snap_example.xml")

(fact "required test xml file exists"
      (.exists (io/as-file test-data-url)) => true)

(fact "will create list of projects"
      (subject/get-projects test-data-url {:server :snap}) => (has every? (contains {:name              string?
                                                                                     :activity          keyword?
                                                                                     :prognosis         keyword?
                                                                                     :last-build-status keyword?
                                                                                     :last-build-label  string?
                                                                                     :last-build-time   anything
                                                                                     :next-build-time   anything
                                                                                     :web-url           string?
                                                                                     :stage             string?
                                                                                     :owner             string?
                                                                                     :branch            string?})))

