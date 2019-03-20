(ns clj-cctray.filtering-test
  (:require [clj-cctray.filtering :as subject]
            [clojure.test :refer :all]
            [java-time :as t]))

(deftest by-name
  (is (= [{:name "project-1"}
          {:name "project-1"}
          {:name "project-2"}
          {:name "project-2"}]
         (subject/by-name ["project-1" "project-2"] [{:name "project-1"}
                                                     {:name "project-1"}
                                                     {:name "project-2"}
                                                     {:name "project-2"}
                                                     {:name "project-3"}
                                                     {:name "project-3"}]))))

(deftest filters-by-date
  (let [date (t/instant "2015-02-01T00:00:00Z")
        before (t/instant "2015-01-01T00:00:00Z")
        after (t/instant "2015-03-01T00:00:00Z")]
    (testing "before"
      (is (= [{:name "project-1" :last-build-time before}]
             (subject/before date [{:name "project-1" :last-build-time before}
                                   {:name "project-2" :last-build-time after}
                                   {:name "project-3" :last-build-time after}]))))

    (testing "after"
      (is (= [{:name "project-3" :last-build-time after}]
             (subject/after date [{:name "project-1" :last-build-time before}
                                  {:name "project-2" :last-build-time before}
                                  {:name "project-3" :last-build-time after}]))))))

(deftest filters-by-prognosis
  (let [projects [{:name "project-1" :prognosis :healthy}
                  {:name "project-2" :prognosis :sick}
                  {:name "project-3" :prognosis :healthy-building}
                  {:name "project-4" :prognosis :sick-building}
                  {:name "project-5" :prognosis :unknown}]]

    (testing "healthy"
      (is (= [{:name "project-1" :prognosis :healthy}] (subject/healthy projects))))

    (testing "sick"
      (is (= [{:name "project-2" :prognosis :sick}] (subject/sick projects))))

    (testing "healthy building"
      (is (= [{:name "project-3" :prognosis :healthy-building}] (subject/healthy-building projects))))

    (testing "sick building"
      (is (= [{:name "project-4" :prognosis :sick-building}] (subject/sick-building projects))))

    (testing "unknown"
      (is (= [{:name "project-5" :prognosis :unknown}] (subject/unknown-prognosis projects))))

    (testing "interesting includes everything but healthy projects"
      (is (= [{:name "project-2" :prognosis :sick}
              {:name "project-3" :prognosis :healthy-building}
              {:name "project-4" :prognosis :sick-building}
              {:name "project-5" :prognosis :unknown}] (subject/interesting projects))))))
