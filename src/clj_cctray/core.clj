(ns clj-cctray.core
  (:require [clj-cctray.parser :as parser]
            [clj-cctray.name :as name]
            [clj-cctray.ci.go-snap :as snap]
            [clj-cctray.util :refer :all]))

(defn- apply-modifiers [fns to-mod]
  (reduce #(%2 %1) to-mod fns))

(def project-options {:go              snap/extract-name
                      :snap            snap/extract-name
                      :normalise-name  name/normalise-name
                      :normalise-stage snap/normalise-stage
                      :normalise-job   snap/normalise-job
                      :normalise       [name/normalise-name, snap/normalise-stage, snap/normalise-job]})

(def project-list-options {:go   snap/distinct-projects
                           :snap snap/distinct-projects})

(defn- parse-options [options mapping]
  (remove nil? (flatten (map #(% mapping) options))))

(defn parse-project-options [options]
  (parse-options options project-options))

(defn parse-project-list-options [options]
  (parse-options options project-list-options))

(defn get-projects [url & {:keys [options]}]
  (map (partial apply-modifiers (parse-project-options options))
       (apply-modifiers (parse-project-list-options options)
                        (parser/get-projects url))))
