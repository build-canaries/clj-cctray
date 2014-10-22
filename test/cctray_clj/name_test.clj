(ns cctray-clj.name-test
  (:require [cctray-clj.name :as subject]
            [midje.sweet :refer :all]))

(facts "sentanceize"
       (fact "hyphens"
             (subject/sentanceize "first-second-third-fourth") => "first second third fourth")

       (fact "underscores"
             (subject/sentanceize "first_second_third_fourth") => "first second third fourth")

       (fact "camelCase"
             (subject/sentanceize "firstSecondThird") => "first Second Third"
             (subject/sentanceize "FirstSecondThird") => "First Second Third")

       (fact "acroynms are not split"
             (subject/sentanceize "ABC") => "ABC"
             (subject/sentanceize "somethingEndingWithABC") => "something Ending With ABC")

       (fact "nil doesn't cause a NullPointerException"
             (subject/sentanceize nil) => nil))

(facts "extract-name"
       (fact "includes the raw name"
             (subject/extract-name "some-name") => (contains {:raw-name "some-name"}))

       (fact "sentanceizes the name"
             (subject/extract-name "some-name") => (contains {:name ..name..})
             (provided
               (subject/sentanceize "some-name") => ..name..
               (subject/sentanceize anything) => irrelevant))

       (facts "ThoughtWorks Go uses :: to delimit the name from the stage and job"
              (fact "extracts the name"
                    (subject/extract-name "name :: stage") => (contains {:name "name"}))

              (fact "extracts the stage"
                    (subject/extract-name "name :: stage") => (contains {:stage "stage"}))

              (fact "sentanceizes the stage"
                    (subject/extract-name "name :: some-stage") => (contains {:stage ..stage..})
                    (provided
                      (subject/sentanceize "some-stage") => ..stage..
                      (subject/sentanceize anything) => irrelevant))

              (fact "extracts the job"
                    (subject/extract-name "name :: stage :: job") => (contains {:job "job"}))

              (fact "sentanceizes the job"
                    (subject/extract-name "name :: stage :: some-job") => (contains {:job ..job..})
                    (provided
                      (subject/sentanceize "some-job") => ..job..
                      (subject/sentanceize anything) => irrelevant))

              (fact "sets stage and job to nil if no delimiter exists (ie. if parsing from non Go CI Servers)"
                    (subject/extract-name "i-am-just-a-name") => (contains {:stage nil
                                                                            :job   nil}))))
