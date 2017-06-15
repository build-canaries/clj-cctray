(ns clj-cctray.owner-test
  (:require [clj-cctray.owner :as subject]
            [midje.sweet :refer :all]))

(facts "project modifiers"
       (fact "normalises owner"
             (subject/normalise-owner {:owner "SomeOwner"}) => (contains {:owner "some owner"}))

       (fact "returns the unnormalised owner"
             (subject/normalise-owner {:owner "SomeOwner"}) => (contains {:unnormalised-owner "SomeOwner"}))

       (fact "handles nil owner"
             (subject/normalise-owner {:owner nil}) => (contains {:owner nil})))
