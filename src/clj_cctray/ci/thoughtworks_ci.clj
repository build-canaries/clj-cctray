(ns clj-cctray.ci.thoughtworks-ci
  "Common functions specific to the ThoughtWorks built CI servers."
  (:require [clj-cctray.util :refer :all]))

(defn normalise-stage
  "Normalises the stage name in the given project map."
  [{:keys [stage]}]
  {:stage (normalise-string stage)})
