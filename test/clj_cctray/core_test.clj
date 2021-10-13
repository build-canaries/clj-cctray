(ns clj-cctray.core-test
  (:require [clj-cctray.core :as subject]
            [clj-cctray.ci.circle-ci :as circle]
            [clj-cctray.ci.concourse :as concourse]
            [clj-cctray.ci.go :as go]
            [clj-cctray.name :as name]
            [clj-cctray.owner :as owner]
            [clj-cctray.dates :as dates]
            [clojure.test :refer :all]))

(deftest project-modifiers
  (testing "unknown options don't cause an error"
    (is (= [] (subject/project-modifiers {}))))

  (testing ":server"
    (testing ":go"
      (is (= [go/split-name] (subject/project-modifiers {:server :go}))))

    (testing ":circle"
      (is (= [circle/split-name] (subject/project-modifiers {:server :circle}))))

    (testing ":concourse"
      (is (= [concourse/split-name] (subject/project-modifiers {:server :concourse})))))

  (testing ":normalise"
    (testing "allows a single key to be supplied"
      (binding [subject/*normalise-partial* identity]
        (is (= [:foo] (subject/project-modifiers {:normalise [:foo]})))))

    (testing "allows multiple keys to be supplied"
      (binding [subject/*normalise-partial* identity]
        (is (= [:foo :bar :baz] (subject/project-modifiers {:normalise [:foo :bar :baz]})))))

    (testing "true"
      (is (= [name/normalise-name, go/normalise-stage, go/normalise-job owner/normalise-owner]
             (subject/project-modifiers {:normalise true})))))

  (testing ":print-dates"
    (testing "uses the iso format by default"
      (binding [subject/*print-dates-partial* (fn [format]
                                              (is (= format dates/iso-format)))]
        (subject/project-modifiers {:print-dates true})))

    (testing "uses the given value if it's a string"
      (binding [subject/*print-dates-partial* (fn [format]
                                              (is (= format "yyyy-MM-dd")))]
        (subject/project-modifiers {:print-dates "yyyy-MM-dd"})))))
