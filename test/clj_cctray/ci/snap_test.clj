(ns clj-cctray.ci.snap-test
  (:require [clj-cctray.ci.snap :as subject]
            [midje.sweet :refer :all]))

(facts "split-name"
       (fact "extracts the name"
             (subject/split-name {:name "irrelevant/name (irrelevant) :: irrelevant"}) => (contains {:name "name"}))

       (fact "extracts the stage"
             (subject/split-name {:name "irrelevant/irrelevant (irrelevant) :: some-stage"}) => (contains {:stage "some-stage"}))

       (fact "extracts the branch"
             (subject/split-name {:name "irrelevant/irrelevant (branch) :: irrelevant"}) => (contains {:branch "branch"}))

       (fact "extracts the owner"
             (subject/split-name {:name "owner/irrelevant (irrelevant) :: irrelevant"}) => (contains {:owner "owner"}))

       (fact "does not include the owner, branch and stage if the format doesn't match (ie. if parsing from non Snap CI Servers)"
             (subject/split-name {:name "i-am-just-a-name"}) =not=> (contains {:stage  anything
                                                                               :owner  anything
                                                                               :branch anything})))

(facts "project modifiers"
       (fact "normalises owner"
             (subject/normalise-owner {:owner "SomeOwner"}) => {:owner "some owner"})

       (fact "handles nil owner"
             (subject/normalise-owner {:owner nil}) => {:owner nil})

       (fact "normalises branch"
             (subject/normalise-branch {:branch "SomeBranch"}) => {:branch "some branch"})

       (fact "handles nil branch"
             (subject/normalise-branch {:branch nil}) => {:branch nil}))