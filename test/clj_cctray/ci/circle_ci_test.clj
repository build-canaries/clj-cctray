(ns clj-cctray.ci.circle-ci-test
  (:require [clj-cctray.ci.circle-ci :as subject]
            [clojure.test :refer :all]))

(deftest split-name
  (testing "extracts the name and owner"
    (is (= {:name "name" :owner "owner"} (subject/split-name {:name "owner/name"}))))

  (testing "does not include the owner if the format doesn't match (ie. if parsing from non CircleCI Servers)"
    (is (nil? (subject/split-name {:name "i-am-just-a-name"})))))
