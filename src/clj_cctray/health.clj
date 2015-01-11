(ns clj-cctray.health
  "Functions for extracting the health from cctray xml."
  (:require [clj-cctray.util :refer :all]))

(defn keyword-status
  "Converts the last build status value into a keyword."
  [{:keys [last-build-status]}]
  {:last-build-status (keywordize-camel last-build-status)})

(defn keyword-activity
  "Converts the activity value into a keyword."
  [{:keys [activity]}]
  {:activity (keywordize-camel activity)})

(defn add-prognosis
  "Returns a prognosis which is based on the last build status and activity."
  [{:keys [last-build-status activity]}]
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