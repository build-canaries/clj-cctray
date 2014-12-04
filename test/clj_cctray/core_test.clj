(ns clj-cctray.core-test
  (:require [clj-cctray.core :as subject]
            [clj-cctray.parser :as parser]
            [clj-time.core :as t]
            [midje.sweet :refer :all]))

(facts "getting projects"
       (fact "works without providing any additional options or modifiers"
             (subject/get-projects ..url..) => [..project..]
             (provided
               (parser/get-projects ..url..) => [..project..]))

       (facts "options"
              (fact "unknown options don't cause an error"
                    (subject/get-projects ..url.. :options [:random-option]) => [..project..]
                    (provided
                      (parser/get-projects ..url..) => [..project..]))

              (fact ":go can be used to enabled go specific parsing"
                    (subject/get-projects ..url.. :options [:go]) => (contains [(contains {:name "name" :stage "stage" :job "job2"})])
                    (provided
                      (parser/get-projects ..url..) => [{:name "name :: stage :: job1" :last-build-time (t/date-time 2014 10 07 14 24 22)}
                                                        {:name "name :: stage :: job2" :last-build-time (t/date-time 2014 10 07 15 24 22)}]))

              (fact ":normalise can be used to normalise names"
                    (subject/get-projects ..url.. :options [:normalise]) => (contains [(contains {:name "some name"})])
                    (provided
                      (parser/get-projects ..url..) => [{:name "SomeName"}]))))
