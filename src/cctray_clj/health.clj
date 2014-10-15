(ns cctray-clj.health)

(defn extract-health [{:keys [lastBuildStatus activity]}]
  (cond
    (and (= lastBuildStatus "Success") (= activity "Sleeping")) {:prognosis "healthy"}
    (and (= lastBuildStatus "Success") (= activity "Building")) {:prognosis "healthy-building"}
    (and (= lastBuildStatus "Failure") (= activity "Sleeping")) {:prognosis "sick"}
    (and (= lastBuildStatus "Failure") (= activity "Building")) {:prognosis "sick-building"}
    :else {:prognosis "unknown"}))