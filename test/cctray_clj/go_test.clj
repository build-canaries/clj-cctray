(ns cctray-clj.go-test
  (:require [cctray-clj.go :as subject]
            [midje.sweet :refer :all]))

(facts "reduce projects"
       (fact "sick building is highest priority"
             (subject/reduce-projects [{:prognosis :sick-building}
                                       {:prognosis :sick}
                                       {:prognosis :healthy-building}
                                       {:prognosis :healthy}
                                       {:prognosis :unknown}]) => {:prognosis :sick-building})

       (fact "sick is 2nd highest priority (if nothing is building)"
             (subject/reduce-projects [{:prognosis :sick}
                                       {:prognosis :healthy}
                                       {:prognosis :unknown}]) => {:prognosis :sick})

       (fact "healthy building is 3rd highest priority"
             (subject/reduce-projects [{:prognosis :healthy-building}
                                       {:prognosis :healthy}
                                       {:prognosis :unknown}]) => {:prognosis :healthy-building})

       (fact "healthy is 4th highest priority"
             (subject/reduce-projects [{:prognosis :unknown}
                                       {:prognosis :healthy}
                                       {:prognosis :unknown}]) => {:prognosis :healthy})

       (fact "unknown is lowest priority"
             (subject/reduce-projects [{:prognosis :unknown}
                                       {:prognosis :unknown}
                                       {:prognosis :unknown}]) => {:prognosis :unknown})

       (fact "if any stage/job is sick and another is building then prognosis is sick-building"
             (subject/reduce-projects [{:prognosis :healthy-building}
                                       {:prognosis :sick}
                                       {:prognosis :healthy}]) => {:prognosis :sick-building}))
