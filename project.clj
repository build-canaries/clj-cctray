(defproject clj-cctray "0.5.1"
            :description "Clojure parser for cctray.xml"
            :url "https://github.com/build-canaries/clj-cctray"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[org.clojure/data.json "0.2.5"]
                           [clj-time "0.8.0"]
                           [clj-http "1.0.1"] ]
            :profiles {:dev {:plugins      [[lein-midje "3.1.3"]
                                            [lein-ancient "0.5.5"]]
                             :dependencies [[org.clojure/clojure "1.6.0"]
                                            [midje "1.6.3"]]}})
