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

(facts "normalise string"
      (fact "lower cases"
            (subject/normalise-string "UPPERCASE") => "uppercase")

      (fact "sentanceizes"
            (subject/normalise-string ..input..) => "..output.."
            (provided
              (subject/sentanceize ..input..) => ..output..)))

(facts "extract-name"
       (fact "includes the unmodified name"
             (subject/extract-name "some-name") => (contains {:unmodified-name "some-name"}))

       (fact "normalises the name"
             (subject/extract-name "some-name") => (contains {:name ..name..})
             (provided
               (subject/normalise-string "some-name") => ..name..
               (subject/normalise-string anything) => irrelevant))

       (facts "ThoughtWorks Go uses :: to delimit the name from the stage and job"
              (fact "the unmodified name doesn't include stage or job"
                    (subject/extract-name "name :: stage :: job") => (contains {:unmodified-name "name"}))

              (fact "extracts and normalises the name"
                    (subject/extract-name "name :: stage :: job") => (contains {:name ..name..})
                    (provided
                      (subject/normalise-string "name") => ..name..
                      (subject/normalise-string anything) => irrelevant))

              (fact "extracts and normalises the stage"
                    (subject/extract-name "name :: some-stage") => (contains {:stage ..stage..})
                    (provided
                      (subject/normalise-string "some-stage") => ..stage..
                      (subject/normalise-string anything) => irrelevant))

              (fact "extracts and normalises the job"
                    (subject/extract-name "name :: stage :: some-job") => (contains {:job ..job..})
                    (provided
                      (subject/normalise-string "some-job") => ..job..
                      (subject/normalise-string anything) => irrelevant))

              (fact "sets stage and job to nil if no delimiter exists (ie. if parsing from non Go CI Servers)"
                    (subject/extract-name "i-am-just-a-name") => (contains {:stage nil
                                                                            :job   nil}))))
