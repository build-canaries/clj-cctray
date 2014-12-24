(ns clj-cctray.ci.thoughtworks-ci
  "Common functions specific to the ThoughtWorks built CI servers."
  (:require [clojure.string :refer [split join]]
            [clj-cctray.name :refer :all]))

(def ^:private prognosis-priorities {#{:sick-building}                   :sick-building
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

(defn- ^:testable pick-prognosis [prognosis-1 prognosis-2]
  (get prognosis-priorities (set [prognosis-1 prognosis-2])))

(defn- by-prognosis [previous current]
  (pick-prognosis previous (:prognosis current)))

(defn- to-single-entry [[_ projects-by-name]]
  (merge (last
           (sort-by (juxt :last-build-time :job) projects-by-name))
         {:prognosis (reduce by-prognosis :unknown projects-by-name)}))

(defn distinct-projects
  "Go and Snap add multiple entries to the cctray xml for a single project for each of its stages and jobs.

  This function filters the given project list to only include each project once, as knowing the overall information
  of a project is generally more useful than knowing about each individual job or stage.

  The additional information for the distinct project will be pulled from the entry with the latest build time,
  except the prognosis which is worked out based on the prognosis of all entries for that project. This is
  because if we just use the prognosis of the latest entry you can end up with healthy building even if a later
  stage is actually sick."
  [all-projects]
  (map to-single-entry (group-by :name all-projects)))

(defn normalise-stage
  "Normalises the stage name in the given project map."
  [project]
  (assoc project :stage (normalise-string (:stage project))))
