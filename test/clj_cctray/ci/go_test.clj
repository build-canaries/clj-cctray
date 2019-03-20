(ns clj-cctray.ci.go-test
  (:require [clj-cctray.ci.go :as subject]
            [clojure.test :refer :all]))

(deftest split-name
  (testing "extracts the name, stage and job"
    (is (= {:name "name" :stage "stage" :job "job"}
           (subject/split-name {:name "name :: stage :: job"}))))

  (testing "sets stage and job to nil if no delimiter exists (ie. if parsing from non Go CI Servers)"
    (is (= {:name "i-am-just-a-name" :stage nil :job nil}
           (subject/split-name {:name "i-am-just-a-name"})))))

(deftest normalise-job
  (testing "normalises job"
    (is (= {:job "some job" :unnormalised-job "SomeJob"}
           (subject/normalise-job {:job "SomeJob"}))))

  (testing "handles nil job"
    (is (= {:job nil} (subject/normalise-job {:job nil})))))

(deftest normalise-stage
  (testing "normalises stage"
    (is (= {:stage "some stage" :unnormalised-stage "SomeStage"}
           (subject/normalise-stage {:stage "SomeStage"}))))

  (testing "handles nil stage"
    (is (= {:stage nil}
           (subject/normalise-stage {:stage nil})))))
