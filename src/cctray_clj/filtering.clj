(ns cctray-clj.filtering
  (:require [clj-time.core :as t]))

(defn by-name [name projects]
  (filter #(= name (:name %)) projects))

(defn before [date projects]
  (filter #(t/before? (:last-build-time %) date) projects))

(defn after [date projects]
  (filter #(t/after? (:last-build-time %) date) projects))

(defn by-prognosis [prognosis projects]
  (filter #(= prognosis (:prognosis %)) projects))

(defn healthy [projects]
  (by-prognosis :healthy projects))

(defn sick [projects]
  (by-prognosis :sick projects))

(defn healthy-building [projects]
  (by-prognosis :healthy-building projects))

(defn sick-building [projects]
  (by-prognosis :sick-building projects))

(defn unknown-prognosis [projects]
  (by-prognosis :unknown projects))

(defn interesting [projects]
  (filter #(not= :healthy (:prognosis %)) projects))
