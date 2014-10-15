(ns cctray-clj.health-test
  (:require [cctray-clj.health :as subject]
            [midje.sweet :refer :all]))

(tabular "will say if the build is healthy"
         (fact (subject/extract-health {:lastBuildStatus ?status :activity ?activity}) => {:prognosis ?healthy})
         ?status ?activity ?healthy
         "Success" "Sleeping" :healthy
         "Success" "Building" :healthy-building
         "Failure" "Sleeping" :sick
         "Failure" "Building" :sick-building
         "random" "random" :unknown
         "Success" nil :unknown
         "Failure" nil :unknown
         nil "Sleeping" :unknown
         nil "Building" :unknown
         nil nil :unknown)

