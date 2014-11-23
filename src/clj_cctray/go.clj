(ns clj-cctray.go
  (:require [clojure.string :refer [split join]]
            [clj-cctray.name :refer :all]))

(def prognosis-priorities {#{:sick-building}                   :sick-building
                           #{:sick-building :sick}             :sick-building
                           #{:sick-building :healthy-building} :sick-building
                           #{:sick-building :healthy}          :sick-building
                           #{:sick-building :unknown}          :sick-building
                           #{:sick}                            :sick
                           #{:sick :healthy-building}          :sick-building
                           #{:sick :healthy}                   :sick
                           #{:sick :unknown}                   :sick
                           #{:healthy-building}                :healthy-building
                           #{:healthy-building :healthy}       :healthy-building
                           #{:healthy-building :unknown}       :healthy-building
                           #{:healthy}                         :healthy
                           #{:healthy :unknown}                :healthy
                           #{:unknown}                         :unknown})

(defn pick-prognosis [prognosis-1 prognosis-2]
  (get prognosis-priorities (set [prognosis-1 prognosis-2])))

(defn- by-prognosis [previous current]
  (pick-prognosis previous (:prognosis current)))

(defn- jobs [project]
  (not (:job project)))

(defn- to-single-entry [[_ projects-by-name]]
  (merge (->>
           (filter jobs projects-by-name)
           (sort-by :last-build-time)
           (last))
         {:prognosis (reduce by-prognosis :unknown projects-by-name)}))

(defn distinct-projects [all-projects]
  (map to-single-entry (group-by :name all-projects)))

(defn- contains-job? [split-name]
  (> (count split-name) 2))

(defn extract-name [{:keys [name]}]
  (let [split-name (split name #"\s::\s")]
    {:name  (first split-name)
     :stage (second split-name)
     :job   (if (contains-job? split-name)
              (last split-name))}))
