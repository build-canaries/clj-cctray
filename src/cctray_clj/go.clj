(ns cctray-clj.go)

(def priorities {#{:sick-building}                   :sick-building
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

(defn- prognosis [accumulated next]
  (merge accumulated {:prognosis (get priorities (set [(:prognosis accumulated) (:prognosis next)]))}))

(defn reduce-projects [projects]
  (reduce prognosis (first projects) projects))
