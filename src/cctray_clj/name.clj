(ns cctray-clj.name
  (:require [clojure.string :refer [split join lower-case]]))

(defn- contains-job? [split-name]
  (> (count split-name) 2))

(defn sentanceize [str]
  (if-not (nil? str)
    (-> str
        (clojure.string/replace #"[-_]+", " ")
        (clojure.string/replace #"([a-z])([A-Z])", "$1 $2"))))

(defn normalise-string [str]
  (if-not (nil? str)
    (lower-case (sentanceize str))))

(defn extract-name [{:keys [name]}]
  (let [split-name (split name #"\s::\s")]
    {:name            (normalise-string (first split-name))
     :unmodified-name (first split-name)
     :stage           (normalise-string (second split-name))
     :job             (if (contains-job? split-name)
                        (normalise-string (last split-name)))}))
