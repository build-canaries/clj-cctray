(defproject clj-cctray "0.10.0"
  :description "Clojure parser for cctray.xml"
  :url "https://github.com/build-canaries/clj-cctray"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-time "0.13.0"]]
  :aliases {"test" ["midje"]}
  :codox {:output-path "doc"}
  :profiles {:dev {:plugins      [[lein-midje "3.2.1"]]
                   :dependencies [[org.clojure/clojure "1.8.0"]
                                  [midje "1.8.3"]]}})
