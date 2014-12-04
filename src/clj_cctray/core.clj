(ns clj-cctray.core
  (:require [clj-cctray.parser :as parser]
            [clj-cctray.name :as name]
            [clj-cctray.ci.go-snap :as snap]
            [clj-cctray.util :refer :all]))

(defn- apply-processors [processors thing]
  (reduce #(%2 %1) thing processors))

(def ^:private all-project-processors {:go              snap/extract-name
                                       :snap            snap/extract-name
                                       :normalise-name  name/normalise-name
                                       :normalise-stage snap/normalise-stage
                                       :normalise-job   snap/normalise-job
                                       :normalise       [name/normalise-name, snap/normalise-stage, snap/normalise-job]})

(def ^:private all-pre-processors {})

(def ^:private all-post-processors {:go   snap/distinct-projects
                                    :snap snap/distinct-projects})

(defn- parse-options [options processor-map]
  (remove nil? (flatten (map #(% processor-map) options))))

(defn project-processors [options]
  (parse-options options all-project-processors))

(defn pre-processors [options]
  (parse-options options all-pre-processors))

(defn post-processors [options]
  (parse-options options all-post-processors))

(defn get-projects [url & {:keys [options]}]
  (apply-processors (post-processors options)
                   (map (partial apply-processors (project-processors options))
                        (apply-processors (pre-processors options)
                                         (parser/get-projects url)))))
