(ns clj-cctray.name-test
  (:require [clj-cctray.name :as subject]
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
