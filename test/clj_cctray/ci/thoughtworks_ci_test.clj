(ns clj-cctray.ci.thoughtworks-ci-test
  (:require [clj-cctray.ci.thoughtworks-ci :as subject]
            [midje.sweet :refer :all]))

(facts "project modifiers"
       (fact "normalises stage"
             (subject/normalise-stage {:stage "SomeStage"}) => (contains {:stage "some stage"}))

       (fact "returns the unnormalised stage"
             (subject/normalise-stage {:stage "SomeStage"}) => (contains {:unnormalised-stage "SomeStage"}))

       (fact "handles nil stage"
             (subject/normalise-stage {:stage nil}) => (contains {:stage nil})))
