(ns clj-cctray.ci.go-test
  (:require [clj-cctray.ci.go :as subject]
            [midje.sweet :refer :all]))

(facts "split-name project modifier splits on the :: delimiter to get the name, stage and job"
       (fact "extracts the name"
             (subject/split-name {:name "name :: stage :: job"}) => (contains {:name "name"}))

       (fact "extracts the stage"
             (subject/split-name {:name "name :: some-stage"}) => (contains {:stage "some-stage"}))

       (fact "extracts the job"
             (subject/split-name {:name "name :: stage :: some-job"}) => (contains {:job "some-job"}))

       (fact "sets stage and job to nil if no delimiter exists (ie. if parsing from non Go CI Servers)"
             (subject/split-name {:name "i-am-just-a-name"}) => (contains {:stage nil
                                                                           :job   nil})))

(facts "project modifiers"
       (fact "normalises job"
             (subject/normalise-job {:job "SomeJob"}) => (contains {:job "some job"}))

       (fact "returns the unnormalised job"
             (subject/normalise-job {:job "SomeJob"}) => (contains {:unnormalised-job "SomeJob"}))

       (fact "handles nil job"
             (subject/normalise-job {:job nil}) => (contains {:job nil}))

       (fact "normalises stage"
             (subject/normalise-stage {:stage "SomeStage"}) => (contains {:stage "some stage"}))

       (fact "returns the unnormalised stage"
             (subject/normalise-stage {:stage "SomeStage"}) => (contains {:unnormalised-stage "SomeStage"}))

       (fact "handles nil stage"
             (subject/normalise-stage {:stage nil}) => (contains {:stage nil})))
