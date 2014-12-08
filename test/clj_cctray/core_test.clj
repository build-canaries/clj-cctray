(ns clj-cctray.core-test
  (:require [clj-cctray.core :as subject]
            [clj-cctray.parser :as parser]
            [clj-time.core :as t]
            [midje.sweet :refer :all]))

(facts "getting projects"
       (fact "works without providing any options"
             (subject/get-projects ..url..) => [..project..]
             (provided
               (parser/get-projects ..url..) => [..project..]))

       (facts "options"
              (fact "unknown options don't cause an error"
                    (subject/get-projects irrelevant :options {:random-option anything}) => [..project..]
                    (provided
                      (parser/get-projects anything) => [..project..]))

              (facts ":server"
                     (fact ":go can be used to enabled go specific parsing"
                           (subject/get-projects irrelevant :options {:server :go}) => (contains [(contains {:name "name" :stage "stage" :job "job2"})])
                           (provided
                             (parser/get-projects anything) => [{:name "name :: stage :: job1" :last-build-time (t/date-time 2014 10 07 14 24 22)}
                                                                {:name "name :: stage :: job2" :last-build-time (t/date-time 2014 10 07 15 24 22)}]))

                     (fact ":snap can be used to enabled go specific parsing"
                           (subject/get-projects irrelevant :options {:server :snap}) => (contains [(contains {:name "name" :stage "stage" :job "job2"})])
                           (provided
                             (parser/get-projects anything) => [{:name "name :: stage :: job1" :last-build-time (t/date-time 2014 10 07 14 24 22)}
                                                                {:name "name :: stage :: job2" :last-build-time (t/date-time 2014 10 07 15 24 22)}])))

              (facts ":normalise"
                     (fact ":name can be used to normalise names"
                           (subject/get-projects irrelevant :options {:normalise :name}) => (contains [(contains {:name "some name" :stage "SomeStage" :job "SomeJob"})])
                           (provided
                             (parser/get-projects anything) => [{:name "SomeName" :stage "SomeStage" :job "SomeJob"}]))

                     (fact ":stage can be used to normalise stages"
                           (subject/get-projects irrelevant :options {:normalise :stage}) => (contains [(contains {:name "SomeName" :stage "some stage" :job "SomeJob"})])
                           (provided
                             (parser/get-projects anything) => [{:name "SomeName" :stage "SomeStage" :job "SomeJob"}]))

                     (fact ":job can be used to normalise jobs"
                           (subject/get-projects irrelevant :options {:normalise :job}) => (contains [(contains {:name "SomeName" :stage "SomeStage" :job "some job"})])
                           (provided
                             (parser/get-projects anything) => [{:name "SomeName" :stage "SomeStage" :job "SomeJob"}]))

                     (fact ":all can be used to normalise everything"
                           (subject/get-projects irrelevant :options {:normalise :all}) => (contains [(contains {:name "some name" :stage "some stage" :job "some job"})])
                           (provided
                             (parser/get-projects anything) => [{:name "SomeName" :stage "SomeStage" :job "SomeJob"}])))))
