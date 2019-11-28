(ns clj-cctray.ci.concourse-test
  (:require [clj-cctray.ci.concourse :as subject]
            [clojure.test :refer :all]))

(deftest split-name
  (testing "extracts the name and job"
    (is (= {:name "name" :job "job"} (subject/split-name {:name "name/job"}))))

  (testing "does not return anything if the format doesn't match (ie. if parsing from non Concourse servers)"
    (is (nil? (subject/split-name {:name "i-am-just-a-name"})))))
