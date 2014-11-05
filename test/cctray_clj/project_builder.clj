(ns cctray-clj.project-builder
  (:require [clj-time.core :as t]))

(defn build [overrides]
  (merge {:name              "some-name"
          :stage             "some-stage"
          :job               "some-job"
          :activity          "Sleeping"
          :last-build-status "Success"
          :last-build-label  "123"
          :last-build-time   (t/now)
          :web-url           "http://host:8153/some-name"
          :prognosis         :healthy}
         overrides))