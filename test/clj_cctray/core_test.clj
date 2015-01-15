(ns clj-cctray.core-test
  (:require [clj-cctray.core :as subject]
            [clj-cctray.parser :as parser]
            [clj-time.core :as t]
            [clj-cctray.ci.go :as go]
            [clj-cctray.ci.snap :as snap]
            [clj-cctray.ci.thoughtworks-ci :as tw]
            [clj-cctray.name :as name]
            [clj-cctray.dates :as dates]
            [midje.sweet :refer :all]
            [midje.util :refer [expose-testables]]))

(expose-testables clj-cctray.core)

(facts "getting projects"
       (fact "works without providing any options"
             (subject/get-projects ..url..) => [..project..]
             (provided
               (parser/get-projects ..url.. anything) => [..project..]))

       (facts "options"
              (fact "unknown options don't cause an error"
                    (project-modifiers {}) => [])

              (facts ":server"
                     (fact ":go"
                           (project-modifiers {:server :go}) => [go/split-name])

                     (fact ":snap"
                           (project-modifiers {:server :snap}) => [snap/split-name]))

              (facts ":normalise"
                     (fact "allows a single key to be supplied"
                           (project-modifiers {:normalise [:foo]}) => irrelevant
                           (provided
                             (#'clj-cctray.core/normalise-partial :foo) => irrelevant))

                     (fact "allows multiple keys to be supplied"
                           (project-modifiers {:normalise [:foo :bar :baz]}) => irrelevant
                           (provided
                             (#'clj-cctray.core/normalise-partial :foo) => irrelevant
                             (#'clj-cctray.core/normalise-partial :bar) => irrelevant
                             (#'clj-cctray.core/normalise-partial :baz) => irrelevant))

                     (fact "true"
                           (project-modifiers {:normalise true}) => [name/normalise-name, tw/normalise-stage, go/normalise-job snap/normalise-owner]))

              (facts ":print-dates"
                     (fact "uses the iso format by default"
                           (project-modifiers {:print-dates true}) => irrelevant
                           (provided
                             (#'clj-cctray.core/print-dates-partial "yyyy-MM-dd'T'HH:mm:ss.SSSZZ") => irrelevant))

                     (fact "uses the given value if it's a string"
                           (project-modifiers {:print-dates "yyyy-MM-dd"}) => irrelevant
                           (provided
                             (#'clj-cctray.core/print-dates-partial "yyyy-MM-dd") => irrelevant)))))
