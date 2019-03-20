(ns clj-cctray.util-test
  (:require [clj-cctray.util :as subject]
            [clojure.test :refer :all]))

(deftest keywordize-camel
  (testing "keywords camelCase strings"
    (is (= :camel-case (subject/keywordize-camel "camelCase"))))

  (testing "keywords camelCase keywords"
    (is (= :camel-case (subject/keywordize-camel :camelCase))))

  (testing "keywords Sentence case strings"
    (is (= :camel (subject/keywordize-camel "Camel"))))

  (testing "keywords acroynms correctly"
    (is (= :something-with-abc (subject/keywordize-camel "somethingWithABC"))))

  (testing "handles nil"
    (is (nil? (subject/keywordize-camel nil))))

  (testing "keywordize keys in a map"
    (is (= {:camel-case                 "irrelevant"
            :something-else-camel-cased "irrelevant"} (subject/keywordize-camel-keys {"camelCase"               "irrelevant"
                                                                                      "somethingElseCamelCased" "irrelevant"})))))

(deftest sentenceize
  (testing "hyphens"
    (is (= "first second third fourth" (subject/sentenceize "first-second-third-fourth")))
    (is (= "first second third fourth" (subject/sentenceize "-first-second-third-fourth-"))))

  (testing "underscores"
    (is (= "first second third fourth" (subject/sentenceize "first_second_third_fourth")))
    (is (= "first second third fourth" (subject/sentenceize "_first_second_third_fourth_"))))

  (testing "camelCase"
    (is (= "first Second Third" (subject/sentenceize "firstSecondThird")))
    (is (= "First Second Third" (subject/sentenceize "FirstSecondThird"))))

  (testing "dot seperated"
    (is (= "first second third" (subject/sentenceize "first.second.third")))
    (is (= "first second third" (subject/sentenceize ".first.second.third."))))

  (testing "version numbers are not split"
    (is (= "1.2.3" (subject/sentenceize "1.2.3")))
    (is (= "version 1.2.3" (subject/sentenceize "version-1.2.3"))))

  (testing "generic placeholders in version numbers are not split"
    (is (= "1.2.x" (subject/sentenceize "1.2.x")))
    (is (= "versionx 1.x" (subject/sentenceize "versionx-1.x")))
    (is (= "1.*" (subject/sentenceize "1.*"))))

  (testing "acronyms are not split"
    (is (= "ABC" (subject/sentenceize "ABC")))
    (is (= "something Ending With ABC" (subject/sentenceize "somethingEndingWithABC"))))

  (testing "nil doesn't cause a NullPointerException"
    (is (nil? (subject/sentenceize nil)))))

(deftest normalise-string
  (testing "lower cases"
    (is (= "uppercase" (subject/normalise-string "UPPERCASE"))))

  (testing "sentanceizes"
    (is (= "version 1.2.3" (subject/normalise-string "VERSION-1.2.3")))))

(deftest normalise-key
  (testing "normalises the given key and adds the unnormalised key"
    (is (= {:foo "some foo" :unnormalised-foo "SomeFoo"} (subject/normalise-key :foo {:foo "SomeFoo"}))))

  (testing "doesn't add the key with a nil value if if didn't exist originally"
    (is (= {} (subject/normalise-key :foo {})))))
