(ns clj-cctray.filtering-test
  (:require [clj-cctray.filtering :as subject]
            [midje.sweet :refer :all]
            [clj-time.core :as t]))

(fact "filters by name"
      (subject/by-name ["project-1" "project-2"] [{:name "project-1"}
                                                  {:name "project-1"}
                                                  {:name "project-2"}
                                                  {:name "project-2"}
                                                  {:name "project-3"}
                                                  {:name "project-3"}]) => [{:name "project-1"}
                                                                            {:name "project-1"}
                                                                            {:name "project-2"}
                                                                            {:name "project-2"}])

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

(facts "filters by prognosis"
       (let [projects [{:name "project-1" :prognosis :healthy}
                       {:name "project-2" :prognosis :sick}
                       {:name "project-3" :prognosis :healthy-building}
                       {:name "project-4" :prognosis :sick-building}
                       {:name "project-5" :prognosis :unknown}]]

         (fact "healthy"
               (subject/healthy projects) => [{:name "project-1" :prognosis :healthy}])

         (fact "sick"
               (subject/sick projects) => [{:name "project-2" :prognosis :sick}])

         (fact "healthy building"
               (subject/healthy-building projects) => [{:name "project-3" :prognosis :healthy-building}])

         (fact "sick building"
               (subject/sick-building projects) => [{:name "project-4" :prognosis :sick-building}])

         (fact "unknown"
               (subject/unknown-prognosis projects) => [{:name "project-5" :prognosis :unknown}])

         (fact "interesting includes everything but healthy projects"
               (subject/interesting projects) => [{:name "project-2" :prognosis :sick}
                                                  {:name "project-3" :prognosis :healthy-building}
                                                  {:name "project-4" :prognosis :sick-building}
                                                  {:name "project-5" :prognosis :unknown}])))