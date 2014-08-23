(ns cctray-clj.parser-test
  (:require [cctray-clj.parser :as subject]
            [midje.sweet :refer :all]))

(fact "will turn xml to map"
      (:content (subject/to-map "resources/test_data.xml")) => (contains
                                                                 [(contains {:attrs {:name            "name1"
                                                                                     :lastBuildStatus "Success"}})]))

(fact "will create list of projects"
      (subject/get-projects "resources/test_data.xml") => [{:name "name1" :lastBuildStatus "Success"}
                                                           {:name "name2" :lastBuildStatus "Success"}
                                                           {:name "name3" :lastBuildStatus "Failure"}])

(facts "will split project attributes to seperate attributes in map"
       (fact "project name"
             (subject/extract-name "name1 :: test :: deploy") => {:name "name1" :pipeline "test" :stage "deploy"}))
