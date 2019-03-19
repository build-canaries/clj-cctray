(ns clj-cctray.health-test
  (:require [clj-cctray.health :as subject]
            [clojure.test :refer :all]))

(deftest keyword-status
  (is (= {:last-build-status :success} (subject/keyword-status {:last-build-status "Success"})))
  (is (= {:last-build-status :failure} (subject/keyword-status {:last-build-status "Failure"}))))

(deftest keyword-activity
  (is (= {:activity :sleeping} (subject/keyword-activity {:activity "Sleeping"})))
  (is (= {:activity :building} (subject/keyword-activity {:activity "Building"}))))

(deftest add-prognosis
  (is (= {:prognosis :healthy} (subject/add-prognosis {:last-build-status :success :activity :sleeping})))
  (is (= {:prognosis :healthy-building} (subject/add-prognosis {:last-build-status :success :activity :building})))
  (is (= {:prognosis :sick} (subject/add-prognosis {:last-build-status :failure :activity :sleeping})))
  (is (= {:prognosis :sick-building} (subject/add-prognosis {:last-build-status :failure :activity :building})))
  (is (= {:prognosis :sick} (subject/add-prognosis {:last-build-status :error :activity :sleeping})))
  (is (= {:prognosis :sick-building} (subject/add-prognosis {:last-build-status :error :activity :building})))
  (is (= {:prognosis :unknown} (subject/add-prognosis {:last-build-status "random" :activity "random"})))
  (is (= {:prognosis :unknown} (subject/add-prognosis {:last-build-status :success :activity nil})))
  (is (= {:prognosis :unknown} (subject/add-prognosis {:last-build-status :failure :activity nil})))
  (is (= {:prognosis :unknown} (subject/add-prognosis {:last-build-status nil :activity :sleeping})))
  (is (= {:prognosis :unknown} (subject/add-prognosis {:last-build-status nil :activity nil}))))
