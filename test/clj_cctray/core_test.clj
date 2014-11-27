(ns clj-cctray.core-test
  (:require [clj-cctray.core :as subject]
            [clj-cctray.parser :as parser]
            [midje.sweet :refer :all]))

(facts "getting projects"
       (fact "works without providing any additional options or modifiers"
             (subject/get-projects ..url..) => [..project..]
             (provided
               (parser/get-projects ..url..) => [..project..]))

       (facts "options"
              (fact "go can be used to enabled go specific parsing"
                    (subject/get-projects ..url.. :options [:random-option]) => [..project..]
                    (provided
                      (parser/get-projects ..url..) => [..project..]))

              (fact "go can be used to enabled go specific parsing"
                    (subject/get-projects ..url.. :options [:go]) => (contains [(contains {:name "name" :stage "stage" :job "job"})])
                    (provided
                      (parser/get-projects ..url..) => [{:name "name :: stage :: job"}]))

              (fact "snap can be used to enable snap specific parsing"
                    (subject/get-projects ..url.. :options [:snap]) => (contains [(contains {:name "name" :stage "stage" :job "job"})])
                    (provided
                      (parser/get-projects ..url..) => [{:name "name :: stage :: job"}]))

              (fact "normalise can be used to normalise names"
                    (subject/get-projects ..url.. :options [:normalise]) => (contains [(contains {:name "some name"})])
                    (provided
                      (parser/get-projects ..url..) => [{:name "SomeName"}]))))
