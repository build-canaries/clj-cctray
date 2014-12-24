(ns clj-cctray.ci.thoughtworks-ci-test
  (:require [clj-cctray.ci.thoughtworks-ci :as subject]
            [midje.sweet :refer :all]
            [midje.util :refer [expose-testables]]
            [clj-time.core :as t]))

(expose-testables clj-cctray.ci.thoughtworks-ci)

(facts "distinct projects"
       (fact "flattens stages and jobs for the same pipeline into a single project entry"
             (subject/distinct-projects [{:name            "service-name"
                                          :stage           "build"
                                          :job             nil
                                          :last-build-time (t/date-time 2014 10 07 14 24 22)
                                          :prognosis       :sick}
                                         {:name            "service-name"
                                          :stage           "build"
                                          :job             "build"
                                          :last-build-time (t/date-time 2014 10 07 14 24 11)
                                          :prognosis       :healthy}
                                         {:name            "service-name"
                                          :stage           "build"
                                          :job             "unit-test"
                                          :last-build-time (t/date-time 2014 10 07 14 24 22)
                                          :prognosis       :sick}
                                         {:name            "service-name"
                                          :stage           "functional"
                                          :job             nil
                                          :last-build-time (t/date-time 2014 10 07 13 12 21)
                                          :prognosis       :healthy}
                                         {:name            "service-name"
                                          :stage           "functional"
                                          :job             "functional"
                                          :last-build-time (t/date-time 2014 10 07 13 12 21)
                                          :prognosis       :healthy}
                                         {:name            "diffrerent-service-name"
                                          :stage           "build"
                                          :job             nil
                                          :last-build-time (t/date-time 2014 10 07 14 24 22)
                                          :prognosis       :healthy-building}
                                         {:name            "diffrerent-service-name"
                                          :stage           "build"
                                          :job             "unit-test"
                                          :last-build-time (t/date-time 2014 10 07 14 24 22)
                                          :prognosis       :healthy-building}
                                         {:name            "diffrerent-service-name"
                                          :stage           "functional"
                                          :job             nil
                                          :last-build-time (t/date-time 2014 10 07 13 12 21)
                                          :prognosis       :sick}
                                         {:name            "diffrerent-service-name"
                                          :stage           "functional"
                                          :job             "functional"
                                          :last-build-time (t/date-time 2014 10 07 13 12 21)
                                          :prognosis       :sick}])
             => [{:name            "service-name"
                  :stage           "build"
                  :job             "unit-test"
                  :last-build-time (t/date-time 2014 10 07 14 24 22)
                  :prognosis       :sick}
                 {:name            "diffrerent-service-name"
                  :stage           "build"
                  :job             "unit-test"
                  :last-build-time (t/date-time 2014 10 07 14 24 22)
                  :prognosis       :sick-building}]))

(facts "pick prognosis"
       (fact "sick building is highest priority"
             (pick-prognosis :sick-building :sick-building) => :sick-building
             (pick-prognosis :sick-building :sick) => :sick-building
             (pick-prognosis :sick-building :healthy-building) => :sick-building
             (pick-prognosis :sick-building :healthy) => :sick-building
             (pick-prognosis :sick-building :unknown) => :sick-building)

       (fact "sick is 2nd highest priority (if nothing is building)"
             (pick-prognosis :sick :sick) => :sick
             (pick-prognosis :sick :healthy) => :sick
             (pick-prognosis :sick :unknown) => :sick)

       (fact "healthy building is 3rd highest priority"
             (pick-prognosis :healthy-building :healthy-building) => :healthy-building
             (pick-prognosis :healthy-building :healthy) => :healthy-building
             (pick-prognosis :healthy-building :unknown) => :healthy-building)

       (fact "healthy is 4th highest priority"
             (pick-prognosis :healthy :healthy) => :healthy
             (pick-prognosis :healthy :unknown) => :healthy)

       (fact "unknown is lowest priority"
             (pick-prognosis :unknown :unknown) => :unknown)

       (fact ":healthy-building and :sick become :sick-building"
             (pick-prognosis :healthy-building :sick) => :sick-building))

(facts "project modifiers"
       (fact "normalises stage"
             (subject/normalise-stage {:stage "SomeStage", :foo :bar}) => {:stage "some stage", :foo :bar})

       (fact "handles nil stage"
             (subject/normalise-stage {:stage nil, :foo :bar}) => {:stage nil, :foo :bar}))
