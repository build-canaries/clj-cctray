(ns clj-cctray.ci.circle-ci-test
  (:require [clj-cctray.ci.circle-ci :as subject]
            [midje.sweet :refer :all]))

(facts "split-name"
       (fact "extracts the name"
             (subject/split-name {:name "irrelevant/name"}) => (contains {:name "name"}))

       (fact "extracts the owner"
             (subject/split-name {:name "owner/irrelevant"}) => (contains {:owner "owner"}))

       (fact "does not include the owner if the format doesn't match (ie. if parsing from non CircleCI Servers)"
             (subject/split-name {:name "i-am-just-a-name"}) =not=> (contains {:owner anything})))
