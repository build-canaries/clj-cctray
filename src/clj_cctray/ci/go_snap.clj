(ns clj-cctray.ci.go-snap
  "Functions specific to the ThoughtWorks Go and Snap CI servers."
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

(defn pick-prognosis [prognosis-1 prognosis-2]
  (get prognosis-priorities (set [prognosis-1 prognosis-2])))

(defn- by-prognosis [previous current]
  (pick-prognosis previous (:prognosis current)))

(defn- stages [project]
  (:job project))

(defn- to-single-entry [[_ projects-by-name]]
  (merge (->>
           (filter stages projects-by-name)
           (sort-by :last-build-time)
           (last))
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

(defn- contains-job? [split-name]
  (> (count split-name) 2))

(defn extract-name
  "Go and Snap combine the project name, stage and job into the cctray xml name attribute, using :: as a delimter.

  This function extracts the real project name as well as the stage and job.

  So instead of:

      {:name \"Project Name :: Stage Name :: Job Name\"}

  You end up with:

      {:name  \"Project Name\"
       :stage \"Stage Name\"
       :job   \"Job Name\"}"
  [project]
  (let [split-name (split (:name project) #"\s::\s")]
    (merge project {:name  (first split-name)
                    :stage (second split-name)
                    :job   (if (contains-job? split-name)
                             (last split-name))})))

(defn normalise-stage
  "Normalises the stage name in the given project map."
  [project]
  (assoc project :stage (normalise-string (:stage project))))

(defn normalise-job
  "Normalises the job name in the given project map."
  [project]
  (assoc project :job (normalise-string (:job project))))
