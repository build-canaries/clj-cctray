(ns cctray-clj.parser-test
  (:require [cctray-clj.parser :as subject]
            [midje.sweet :refer :all]))

(tabular "will say if the build is healthy"
         (fact (subject/extract-health {:lastBuildStatus ?status :activity ?activity}) => {:prognosis ?healthy})
         ?status ?activity ?healthy
         "Success" "Sleeping" "healthy"
         "Success" "Building" "healthy-building"
         "Failure" "Sleeping" "sick"
         "Failure" "Building" "sick-building"
         "random" "random" "unknown")

(fact "can filter out green projects"
      (subject/get-interesting-projects anything) =>
      [{:prognosis "healthy-building"}
       {:prognosis "sick-building"}
       {:prognosis "sick"}]
      (provided
        (subject/get-projects anything) => [{:prognosis "healthy-building"}
                                            {:prognosis "sick-building"}
                                            {:prognosis "healthy"}
                                            {:prognosis "sick"}]))
