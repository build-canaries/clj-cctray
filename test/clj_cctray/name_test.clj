(ns clj-cctray.name-test
  (:require [clj-cctray.name :as subject]
            [midje.sweet :refer :all]))

(facts "project modifiers"
       (fact "normalises name"
             (subject/normalise-name {:name "SomeName"}) => (contains {:name "some name"}))

       (fact "returns the unnormalised name"
             (subject/normalise-name {:name "SomeName"}) => (contains {:unnormalised-name "SomeName"})))
