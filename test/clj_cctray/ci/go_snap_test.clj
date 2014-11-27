(ns clj-cctray.ci.go-snap-test
  (:require [clj-cctray.ci.go-snap :as subject]
            [midje.sweet :refer :all]
            [clj-time.core :as t]))

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
                  :job             nil
                  :last-build-time (t/date-time 2014 10 07 14 24 22)
                  :prognosis       :sick}
                 {:name            "diffrerent-service-name"
                  :stage           "build"
                  :job             nil
                  :last-build-time (t/date-time 2014 10 07 14 24 22)
                  :prognosis       :sick-building}]))

(facts "pick prognosis"
       (fact "sick building is highest priority"
             (subject/pick-prognosis :sick-building :sick-building) => :sick-building
             (subject/pick-prognosis :sick-building :sick) => :sick-building
             (subject/pick-prognosis :sick-building :healthy-building) => :sick-building
             (subject/pick-prognosis :sick-building :healthy) => :sick-building
             (subject/pick-prognosis :sick-building :unknown) => :sick-building)

       (fact "sick is 2nd highest priority (if nothing is building)"
             (subject/pick-prognosis :sick :sick) => :sick
             (subject/pick-prognosis :sick :healthy) => :sick
             (subject/pick-prognosis :sick :unknown) => :sick)

       (fact "healthy building is 3rd highest priority"
             (subject/pick-prognosis :healthy-building :healthy-building) => :healthy-building
             (subject/pick-prognosis :healthy-building :healthy) => :healthy-building
             (subject/pick-prognosis :healthy-building :unknown) => :healthy-building)

       (fact "healthy is 4th highest priority"
             (subject/pick-prognosis :healthy :healthy) => :healthy
             (subject/pick-prognosis :healthy :unknown) => :healthy)

       (fact "unknown is lowest priority"
             (subject/pick-prognosis :unknown :unknown) => :unknown)

       (fact ":healthy-building and :sick become :sick-building"
             (subject/pick-prognosis :healthy-building :sick) => :sick-building))

(facts "extract-name project modifier splits on the :: delimiter to get the name, stage and job"
       (fact "extracts the name"
             (subject/extract-name {:name "name :: stage :: job"}) => (contains {:name "name"}))

       (fact "extracts the stage"
             (subject/extract-name {:name "name :: some-stage"}) => (contains {:stage "some-stage"}))

       (fact "extracts the job"
             (subject/extract-name {:name "name :: stage :: some-job"}) => (contains {:job "some-job"}))

       (fact "sets stage and job to nil if no delimiter exists (ie. if parsing from non Go CI Servers)"
             (subject/extract-name {:name "i-am-just-a-name"}) => (contains {:stage nil
                                                                             :job   nil})))

(facts "project modifiers"
       (fact "normalises stage"
             (subject/normalise-stage {:stage "SomeStage", :foo :bar}) => {:stage "some stage", :foo :bar})

       (fact "handles nil stage"
             (subject/normalise-stage {:stage nil, :foo :bar}) => {:stage nil, :foo :bar})

       (fact "normalises job"
             (subject/normalise-job {:job "SomeJob", :foo :bar}) => {:job "some job", :foo :bar})

       (fact "handles nil job"
             (subject/normalise-job {:job nil, :foo :bar}) => {:job nil, :foo :bar}))
