(ns clj-cctray.reader
  (:require [org.httpkit.client :as httpkit]
            [clojure.string :refer [lower-case]]))

(def ^:dynamic http-kit-insecure? true)
(def ^:dynamic http-kit-timeout 30)

(defn http-kit-options [] {:insecure? http-kit-insecure?
                           :timeout   (* http-kit-timeout 1000)
                           :headers   {"Accept" "application/xml"}})

(defn http-read-unsecured-httpkit [url]
  (:body @(httpkit/get url (http-kit-options))))

(defn http-read-unsecured->String [url]
  (let [raw-response (http-read-unsecured-httpkit url)]
    (java.io.ByteArrayInputStream. (.getBytes raw-response))))

(defn decide-reader [url]
  (if (.startsWith (lower-case url) "http") :httpkit :standard))

(def readers {:httpkit  http-read-unsecured->String
              :standard identity})

(defn xml-reader [url]
  (let [reader (decide-reader url)]
    ((reader readers) url)))
