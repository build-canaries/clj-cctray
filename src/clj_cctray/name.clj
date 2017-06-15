(ns clj-cctray.name
  "Functions for normalising and converting names."
  (:require [clj-cctray.util :refer :all])
  (:refer-clojure :exclude [replace]))

(defn normalise-name
  "Normalise the project name in the given project map."
  [{:keys [name]}]
  {:name (normalise-string name) :unnormalised-name name})
