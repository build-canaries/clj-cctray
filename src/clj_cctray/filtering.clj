(ns clj-cctray.filtering
  "Functions for filtering the project list."
  (:require [java-time.core :as t]
            [clj-cctray.util :refer :all]))

(defn by-name
  "Filters projects to only include the projects whose names exactly match those in the names list."
  [names projects]
  (filter #(in? names (:name %)) projects))

(defn before
  "Filters projects to only include those who last built before the given date time."
  [date projects]
  (filter #(t/before? (:last-build-time %) date) projects))

(defn after
  "Filters projects to only include those who last built after the given date time."
  [date projects]
  (filter #(t/after? (:last-build-time %) date) projects))

(defn by-prognosis
  "Filters projects to only include those in the given prognosis list."
  [prognosis projects]
  (filter #(in? prognosis (:prognosis %)) projects))

(defn healthy
  "Filters projects to only include projects with a healthy prognosis."
  [projects]
  (by-prognosis [:healthy] projects))

(defn sick
  "Filters projects to only include projects with a sick prognosis."
  [projects]
  (by-prognosis [:sick] projects))

(defn healthy-building
  "Filters projects to only include projects with a healthy building prognosis."
  [projects]
  (by-prognosis [:healthy-building] projects))

(defn sick-building
  "Filters projects to only include projects with a sick building prognosis."
  [projects]
  (by-prognosis [:sick-building] projects))

(defn unknown-prognosis
  "Filters projects to only include projects with an unknown prognosis."
  [projects]
  (by-prognosis [:unknown] projects))

(defn interesting
  "Filters projects to only include sick, healthy building, sick building or projects with an unknown prognosis"
  [projects]
  (by-prognosis [:sick :healthy-building :sick-building :unknown] projects))
