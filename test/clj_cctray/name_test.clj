(ns clj-cctray.name-test
  (:require [clj-cctray.name :as subject]
            [midje.sweet :refer :all]))

(facts "project modifiers"
       (fact "normalises name"
             (subject/normalise-name {:name "SomeName", :foo :bar}) => {:name "some name", :foo :bar}))
