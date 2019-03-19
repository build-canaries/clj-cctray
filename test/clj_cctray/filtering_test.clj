(ns clj-cctray.filtering-test
  (:require [clj-cctray.filtering :as subject]
            [clojure.test :refer :all]
            [clj-time.core :as t]))

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
  (let [date (t/date-time 2015 2 1 0 0 0)
        before (t/date-time 2015 1 1 0 0 0)
        after (t/date-time 2015 3 1 0 0 0)]
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
