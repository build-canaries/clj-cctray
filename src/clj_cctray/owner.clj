(ns clj-cctray.owner
  "Functions for normalising and converting owners."
  (:require [clj-cctray.util :refer :all])
  (:refer-clojure :exclude [replace]))

(defn normalise-owner
  "Normalises the owner name in the given project map."
  [{:keys [owner]}]
  {:owner (normalise-string owner) :unnormalised-owner owner})
