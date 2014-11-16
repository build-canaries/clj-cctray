(ns cctray-clj.name-test
  (:require [cctray-clj.name :as subject]
            [midje.sweet :refer :all]))

(facts "sentenceize"
       (fact "hyphens"
             (subject/sentenceize "first-second-third-fourth") => "first second third fourth")

       (fact "underscores"
             (subject/sentenceize "first_second_third_fourth") => "first second third fourth")

       (fact "camelCase"
             (subject/sentenceize "firstSecondThird") => "first Second Third"
             (subject/sentenceize "FirstSecondThird") => "First Second Third")

       (fact "acroynms are not split"
             (subject/sentenceize "ABC") => "ABC"
             (subject/sentenceize "somethingEndingWithABC") => "something Ending With ABC")

       (fact "nil doesn't cause a NullPointerException"
             (subject/sentenceize nil) => nil))

(facts "normalise string"
       (fact "lower cases"
             (subject/normalise-string "UPPERCASE") => "uppercase")

       (fact "sentanceizes"
             (subject/normalise-string ..input..) => "..output.."
             (provided
               (subject/sentenceize ..input..) => ..output..)))

(facts "extract-name"
       (fact "normalises the name"
             (subject/extract-name {:name "some-name"}) => (contains {:project-name ..name..})
             (provided
               (subject/normalise-string "some-name") => ..name..
               (subject/normalise-string anything) => irrelevant))

       (facts "ThoughtWorks Go uses :: to delimit the name from the stage and job"
              (fact "replaces :name with the extracted name unnormalised"
                    (subject/extract-name {:name "SomeName :: stage :: job"}) => (contains {:name "SomeName"}))

              (fact "extracts and normalises the project name"
                    (subject/extract-name {:name "name :: stage :: job"}) => (contains {:project-name ..name..})
                    (provided
                      (subject/normalise-string "name") => ..name..
                      (subject/normalise-string anything) => irrelevant))

              (fact "extracts and normalises the stage"
                    (subject/extract-name {:name "name :: some-stage"}) => (contains {:stage ..stage..})
                    (provided
                      (subject/normalise-string "some-stage") => ..stage..
                      (subject/normalise-string anything) => irrelevant))

              (fact "extracts and normalises the job"
                    (subject/extract-name {:name "name :: stage :: some-job"}) => (contains {:job ..job..})
                    (provided
                      (subject/normalise-string "some-job") => ..job..
                      (subject/normalise-string anything) => irrelevant))

              (fact "sets stage and job to nil if no delimiter exists (ie. if parsing from non Go CI Servers)"
                    (subject/extract-name {:name "i-am-just-a-name"}) => (contains {:stage nil
                                                                                    :job   nil}))))
