(ns cctray-clj.health-test
  (:require [cctray-clj.health :as subject]
            [midje.sweet :refer :all]))

(fact "keyword last build status"
      (subject/keyword-status {:last-build-status "Success"}) => {:last-build-status :success}
      (subject/keyword-status {:last-build-status "Failure"}) => {:last-build-status :failure})

(fact "keyword activity"
      (subject/keyword-activity {:activity "Sleeping"}) => {:activity :sleeping}
      (subject/keyword-activity {:activity "Building"}) => {:activity :building})

(tabular "will say if the build is healthy"
         (fact (subject/add-prognosis {:last-build-status ?status :activity ?activity}) => {:prognosis ?healthy})
         ?status ?activity ?healthy
         :success :sleeping :healthy
         :success :building :healthy-building
         :failure :sleeping :sick
         :failure :building :sick-building
         "random" "random" :unknown
         :success nil :unknown
         :failure nil :unknown
         nil :sleeping :unknown
         nil :building :unknown
         nil nil :unknown)

