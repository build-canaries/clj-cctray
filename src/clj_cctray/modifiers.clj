(ns clj-cctray.modifiers
  (:require [clj-cctray.name :refer :all]))

(defn normalise-name [project]
  (assoc project :name (normalise-string (:name project))))

(defn normalise-stage [project]
  (assoc project :stage (normalise-string (:stage project))))

(defn normalise-job [project]
  (assoc project :job (normalise-string (:job project))))
