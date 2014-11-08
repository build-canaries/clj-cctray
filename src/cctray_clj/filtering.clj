(ns cctray-clj.filtering
  (:require [clj-time.core :as t]))

(defn by-name [name projects]
  (filter #(= name (:name %)) projects))

(defn before [date projects]
  (filter #(t/before? (:last-build-time %) date) projects))

(defn after [date projects]
  (filter #(t/after? (:last-build-time %) date) projects))