(ns clj-cctray.modifiers-test
  (:require [clj-cctray.modifiers :as subject]
            [midje.sweet :refer :all]))

(facts "project modifiers"
       (fact "normalises name"
             (subject/normalise-name {:name "SomeName", :foo :bar}) => {:name "some name", :foo :bar})

       (fact "normalises stage"
             (subject/normalise-stage {:stage "SomeStage", :foo :bar}) => {:stage "some stage", :foo :bar})

       (fact "handles nil stage"
             (subject/normalise-stage {:stage nil, :foo :bar}) => {:stage nil, :foo :bar})

       (fact "normalises job"
             (subject/normalise-job {:job "SomeJob", :foo :bar}) => {:job "some job", :foo :bar})

       (fact "handles nil job"
             (subject/normalise-job {:job nil, :foo :bar}) => {:job nil, :foo :bar}))
