(ns clj-cctray.reader_test
  (:require [clj-cctray.reader :as subject]
            [org.httpkit.client :as httpkit]
            [midje.sweet :refer :all]))

(facts "can decide what to use as a reader"
       (fact "uses string as reader if not https"
             (subject/decide-reader "some/file") => :standard)

       (fact "uses httpkit as reader if https"
             (subject/decide-reader "https://someurl") => :httpkit))

(facts "can dispatch to a reader"
       (fact "calls httpkit when reading https url"
             (subject/xml-reader "https://someurl") => anything
             (provided
               (httpkit/get anything anything) => (atom {:body ""})))

       (fact "returns straight url if not https"
             (subject/xml-reader "something") => "something"))

(facts "rebinding allows http kit options to be changed"
       (fact "default http kit options"
             (subject/http-kit-options) => (contains {:insecure? true
                                                      :timeout   30000}))

       (fact "insecure?"
             (binding [subject/http-kit-insecure? false] (subject/http-kit-options)) => (contains {:insecure? false}))

       (fact "timeout"
             (binding [subject/http-kit-timeout 1337] (subject/http-kit-options)) => (contains {:timeout 1337000})))
