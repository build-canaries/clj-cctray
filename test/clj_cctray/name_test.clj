(ns clj-cctray.name-test
  (:require [clj-cctray.name :as subject]
            [clojure.test :refer :all]))

(deftest normalise-name
  (testing "normalises name"
    (is (= {:name              "some name"
            :unnormalised-name "SomeName"} (subject/normalise-name {:name "SomeName"})))))
