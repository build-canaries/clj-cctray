(ns clj-cctray.filtering
  (:require [clj-time.core :as t]
            [clj-cctray.util :refer :all]))

(defn by-name [name projects]
  (filter #(in? name (:name %)) projects))

(defn before [date projects]
  (filter #(t/before? (:last-build-time %) date) projects))

(defn after [date projects]
  (filter #(t/after? (:last-build-time %) date) projects))

(defn by-prognosis [prognosis projects]
  (filter #(in? prognosis (:prognosis %)) projects))

(defn healthy [projects]
  (by-prognosis [:healthy] projects))

(defn sick [projects]
  (by-prognosis [:sick] projects))

(defn healthy-building [projects]
  (by-prognosis [:healthy-building] projects))

(defn sick-building [projects]
  (by-prognosis [:sick-building] projects))

(defn unknown-prognosis [projects]
  (by-prognosis [:unknown] projects))

(defn interesting [projects]
  (by-prognosis [:sick :healthy-building :sick-building :unknown] projects))
