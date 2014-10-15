(ns cctray-clj.health)

(defn extract-health [{:keys [last-build-status activity]}]
  (cond
    (and (= last-build-status "Success") (= activity "Sleeping")) {:prognosis :healthy}
    (and (= last-build-status "Success") (= activity "Building")) {:prognosis :healthy-building}
    (and (= last-build-status "Failure") (= activity "Sleeping")) {:prognosis :sick}
    (and (= last-build-status "Failure") (= activity "Building")) {:prognosis :sick-building}
    :else {:prognosis :unknown}))