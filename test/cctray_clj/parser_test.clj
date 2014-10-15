(ns cctray-clj.parser-test
  (:require [cctray-clj.parser :as subject]
            [midje.sweet :refer :all]))

(fact "can filter out green projects"
      (subject/get-interesting-projects anything) => [{:prognosis :healthy-building}
                                                      {:prognosis :sick-building}
                                                      {:prognosis :sick}]
      (provided
        (subject/get-projects anything) => [{:prognosis :healthy-building}
                                            {:prognosis :sick-building}
                                            {:prognosis :healthy}
                                            {:prognosis :sick}]))
