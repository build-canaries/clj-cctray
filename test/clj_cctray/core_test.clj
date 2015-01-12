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
                    (subject/get-projects irrelevant {:random-option anything}) => [..project..]
                    (provided
                      (parser/get-projects anything) => [..project..]))

              (facts ":server"
                     (fact ":go can be used to enabled go specific parsing"
                           (subject/get-projects irrelevant {:server :go}) => (contains [(contains {:name "name" :stage "stage" :job "job2"})])
                           (provided
                             (parser/get-projects anything) => [{:name "name :: stage :: job1" :last-build-time (t/date-time 2014 10 07 14 24 22)}
                                                                {:name "name :: stage :: job2" :last-build-time (t/date-time 2014 10 07 15 24 22)}]))

                     (fact ":snap can be used to enabled snap specific parsing"
                           (subject/get-projects irrelevant {:server :snap}) => (contains [(contains {:name "name" :stage "stage2" :owner "owner" :branch "branch"})])
                           (provided
                             (parser/get-projects anything) => [{:name "owner/name (branch) :: stage" :last-build-time (t/date-time 2014 10 07 14 24 22)}
                                                                {:name "owner/name (branch) :: stage2" :last-build-time (t/date-time 2014 10 07 15 24 22)}])))

              (facts ":normalise"
                     (fact "can take a collection with a keyword to normalise"
                           (subject/get-projects irrelevant {:normalise [:name]}) => (contains [(contains {:name "some name" :stage "SomeStage" :job "SomeJob"})])
                           (provided
                             (parser/get-projects anything) => [{:name "SomeName" :stage "SomeStage" :job "SomeJob"}]))

                     (fact "can take a collection of mulitple keywords to normalise"
                           (subject/get-projects irrelevant {:normalise [:name :stage]}) => (contains [(contains {:name "some name" :stage "some stage" :job "SomeJob"})])
                           (provided
                             (parser/get-projects anything) => [{:name "SomeName" :stage "SomeStage" :job "SomeJob"}]))

                     (fact "doesn't add the key with a nil value to the map if it didn't exist in the first place"
                           (subject/get-projects irrelevant {:normalise [:foo]}) =not=> (contains [(contains {:foo anything})])
                           (provided
                             (parser/get-projects anything) => [{}]))

                     (fact "true can be used to normalise the name, stage, job and owner"
                           (subject/get-projects irrelevant {:normalise true}) => (contains [(contains {:name "some name" :stage "some stage" :job "some job" :owner "some owner"})])
                           (provided
                             (parser/get-projects anything) => [{:name "SomeName" :stage "SomeStage" :job "SomeJob" :owner "SomeOwner"}])))))
