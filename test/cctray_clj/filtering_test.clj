(ns cctray-clj.filtering-test
  (:require [cctray-clj.filtering :as subject]
            [midje.sweet :refer :all]
            [clj-time.core :as t]))

(fact "filters by name"
      (subject/by-name "project-1" [{:name "project-1"}
                                    {:name "project-1"}
                                    {:name "project-2"}
                                    {:name "project-2"}
                                    {:name "project-3"}
                                    {:name "project-3"}]) => [{:name "project-1"}
                                                              {:name "project-1"}])

(facts "filters by date"
       (fact "before"
             (subject/before ..date.. [{:name "project-1" :last-build-time ..before..}
                                       {:name "project-2" :last-build-time ..after..}
                                       {:name "project-3" :last-build-time ..after..}]) => (contains [(contains {:name "project-1"})])
             (provided
               (t/before? ..before.. ..date..) => true
               (t/before? ..after.. ..date..) => false))

       (fact "after"
             (subject/after ..date.. [{:name "project-1" :last-build-time ..before..}
                                      {:name "project-2" :last-build-time ..before..}
                                      {:name "project-3" :last-build-time ..after..}]) => (contains [(contains {:name "project-3"})])
             (provided
               (t/after? ..before.. ..date..) => false
               (t/after? ..after.. ..date..) => true)))