(ns cctray-clj.project-builder)

(defn build-project [overrides]
  (merge {:name              "some-name"
          :stage             "some-stage"
          :job               "some-job"
          :activity          "Sleeping"
          :last-build-status "Success"
          :last-build-label  "123"
          :last-build-time   nil
          :web-url           "http://host:8153/some-name"
          :prognosis         :healthy}
         overrides))