(ns clj-cctray.util-test
  (:require [clj-cctray.util :as subject]
            [midje.sweet :refer :all]))

(fact "keywords camelCase strings"
      (subject/keywordize-camel "camelCase") => :camel-case)

(fact "keywords camelCase keywords"
      (subject/keywordize-camel :camelCase) => :camel-case)

(fact "keywords Sentence case strings"
      (subject/keywordize-camel "Camel") => :camel)

(fact "keywords acroynms correctly"
      (subject/keywordize-camel "somethingWithABC") => :something-with-abc)

(fact "handles nil"
      (subject/keywordize-camel nil) => nil)

(fact "keywordize keys in a map"
      (subject/keywordize-camel-keys {"camelCase"               irrelevant
                                      "somethingElseCamelCased" irrelevant}) => {:camel-case                 irrelevant
                                                                                 :something-else-camel-cased irrelevant})

(facts "sentenceize"
       (fact "hyphens"
             (subject/sentenceize "first-second-third-fourth") => "first second third fourth")

       (fact "underscores"
             (subject/sentenceize "first_second_third_fourth") => "first second third fourth")

       (fact "camelCase"
             (subject/sentenceize "firstSecondThird") => "first Second Third"
             (subject/sentenceize "FirstSecondThird") => "First Second Third")

       (fact "dot seperated"
             (subject/sentenceize "first.second.third") => "first second third"
             (subject/sentenceize ".first.second.third.0.1.2.abc.") => "first second third 0.1.2 abc")

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

(facts "normalise keys"
       (fact "normalises the given key"
             (subject/normalise-key :foo {:foo "SomeFoo"}) => {:foo "some foo"})

       (fact "doesn't add the key with a nil value if if didn't exist originally"
             (subject/normalise-key :foo {}) => {}))