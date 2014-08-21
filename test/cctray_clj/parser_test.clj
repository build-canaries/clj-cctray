(ns cctray-clj.parser-test
  (:require [cctray-clj.parser :as subject]
            [midje.sweet :refer :all]))

(fact "will turn xml to map"
      (:content (subject/to-map "resources/test_data.xml"))
      => (contains [(contains {:attrs {:name "some name" :lastBuildStatus "Success"}})]))
