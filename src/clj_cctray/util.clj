(ns clj-cctray.util)

(defn in? [seq elm]
  (some #(= elm %) seq))