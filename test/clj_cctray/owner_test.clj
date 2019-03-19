(ns clj-cctray.owner-test
  (:require [clj-cctray.owner :as subject]
            [clojure.test :refer :all]))

(deftest normalise-owner
  (testing "normalises owner"
    (is (= {:owner "some owner" :unnormalised-owner "SomeOwner"}
           (subject/normalise-owner {:owner "SomeOwner"}))))

  (testing "handles nil owner"
    (is (= {:owner nil :unnormalised-owner nil}
           (subject/normalise-owner {:owner nil})))))
