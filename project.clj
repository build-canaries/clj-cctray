(defproject clj-cctray "0.9.0"
            :description "Clojure parser for cctray.xml"
            :url "https://github.com/build-canaries/clj-cctray"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[clj-time "0.10.0"]]
            :profiles {:dev {:plugins      [[lein-midje "3.1.3"]
                                            [lein-ancient "0.6.7"]
                                            [codox "0.8.13"]]
                             :dependencies [[org.clojure/clojure "1.7.0"]
                                            [midje "1.7.0"]]}})
