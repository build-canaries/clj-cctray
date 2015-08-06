(ns clj-cctray.ci.thoughtworks-ci-test
  (:require [clj-cctray.ci.thoughtworks-ci :as subject]
            [midje.sweet :refer :all]))

(facts "project modifiers"
       (fact "normalises stage"
             (subject/normalise-stage {:stage "SomeStage"}) => {:stage "some stage"})

       (fact "handles nil stage"
             (subject/normalise-stage {:stage nil}) => {:stage nil}))
