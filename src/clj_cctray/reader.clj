(ns clj-cctray.reader
  (:require [org.httpkit.client :as httpkit]))

(defn http-read-unsecured-httpkit [url]
  (:body @(httpkit/get url {:insecure? true})))

(defn http-read-unsecured->String [url]
  (let [raw-response (http-read-unsecured-httpkit url)]
    (java.io.ByteArrayInputStream. (.getBytes raw-response))))

(defn decide-reader [url]
  (if (.startsWith url "https") :httpkit :standard))

(def readers {:httpkit http-read-unsecured->String
              :standard identity})

(defn xml-reader [url]
  (let [reader (decide-reader url)]
    ((reader readers) url)))