(ns clj-cctray.ci.circle-ci-test
  (:require [clj-cctray.ci.circle-ci :as subject]
            [clojure.test :refer :all]))

(deftest split-name
  (testing "extracts the name and owner"
    (is (= {:name "name" :owner "owner"} (subject/split-name {:name "owner/name"}))))

  (testing "does not return anything if the format doesn't match (ie. if parsing from non CircleCI servers)"
    (is (nil? (subject/split-name {:name "i-am-just-a-name"})))))
