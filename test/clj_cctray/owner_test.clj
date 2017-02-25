(ns clj-cctray.owner-test
  (:require [clj-cctray.owner :as subject]
            [midje.sweet :refer :all]))

(facts "project modifiers"
       (fact "normalises owner"
             (subject/normalise-owner {:owner "SomeOwner"}) => {:owner "some owner"})

       (fact "handles nil owner"
             (subject/normalise-owner {:owner nil}) => {:owner nil}))
