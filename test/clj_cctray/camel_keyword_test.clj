(ns clj-cctray.camel-keyword-test
  (:require [clj-cctray.camel-keyword :as subject]
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