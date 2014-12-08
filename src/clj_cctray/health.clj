(ns clj-cctray.health
  (:require [clj-cctray.camel-keyword :refer :all]))

(defn keyword-status [{:keys [last-build-status]}]
  {:last-build-status (keywordize-camel last-build-status)})

(defn keyword-activity [{:keys [activity]}]
  {:activity (keywordize-camel activity)})

(defn add-prognosis [{:keys [last-build-status activity]}]
  (let [sleeping (= activity :sleeping)
        building (= activity :building)
        success (= last-build-status :success)
        failure (contains? #{:error :failure} last-build-status)]
    (cond
      (and success sleeping) {:prognosis :healthy}
      (and success building) {:prognosis :healthy-building}
      (and failure sleeping) {:prognosis :sick}
      (and failure building) {:prognosis :sick-building}
      :else {:prognosis :unknown})))