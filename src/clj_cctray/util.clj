(ns clj-cctray.util
  "Utility functions.")

(defn in?
  "Returns true if the given element is in the given sequence."
  [seq elm]
  (some #(= elm %) seq))