(ns cctray-clj.health
  (:require [cctray-clj.camel-keyword :refer :all]))

(defn keyword-status [{:keys [last-build-status]}]
  {:last-build-status (keywordize-camel last-build-status)})

(defn keyword-activity [{:keys [activity]}]
  {:activity (keywordize-camel activity)})

(defn add-prognosis [{:keys [last-build-status activity]}]
  (cond
    (and (= last-build-status :success) (= activity :sleeping)) {:prognosis :healthy}
    (and (= last-build-status :success) (= activity :building)) {:prognosis :healthy-building}
    (and (= last-build-status :failure) (= activity :sleeping)) {:prognosis :sick}
    (and (= last-build-status :failure) (= activity :building)) {:prognosis :sick-building}
    :else {:prognosis :unknown}))