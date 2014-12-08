(ns clj-cctray.reader
  (:require [clj-http.client :as client]
            [clojure.string :refer [lower-case]]))

(def ^:dynamic http-client-insecure? true)
(def ^:dynamic http-client-timeout 30)

(defn http-client-options [] {:insecure? http-client-insecure?
                           :timeout   (* http-client-timeout 1000)
                           :headers   {"Accept" "application/xml"}})

(defn http-get [url]
  (:body (client/get url (http-client-options))))

(defn http-get->String [url]
  (let [raw-response (http-get url)]
    (java.io.ByteArrayInputStream. (.getBytes raw-response))))

(defn decide-reader [url]
  (if (.startsWith (lower-case url) "http") :http-client :standard))

(def readers {:http-client  http-get->String
              :standard identity})

(defn xml-reader [url]
  (let [reader (decide-reader url)]
    ((reader readers) url)))
