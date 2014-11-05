(ns cctray-clj.go)

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
