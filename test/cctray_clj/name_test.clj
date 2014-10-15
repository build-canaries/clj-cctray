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
             (subject/sentanceize "somethingEndingWithABC") => "something Ending With ABC"))

(facts "extract-name"
       (fact "includes the raw name"
             (subject/extract-name "some-name") => (contains {:raw-name "some-name"}))

       (fact "sentanceizes the name"
             (subject/extract-name "some-name") => (contains {:name "some name"})
             (provided
               (subject/sentanceize "some-name") => "some name"))

       (facts "ThoughtWorks Go uses :: to delimit the name from the stage and job"
              (fact "extracts the name"
                    (subject/extract-name "name :: stage") => (contains {:name "name"}))

              (fact "extracts the stage"
                    (subject/extract-name "name :: stage") => (contains {:stage "stage"}))

              (fact "extracts the job"
                    (subject/extract-name "name :: stage :: job") => (contains {:job "job"}))

              (fact "doesn't error when used for non Go CI servers"
                    (subject/extract-name "i-am-just-a-name") => (contains {:stage nil
                                                                            :job   nil}))))
