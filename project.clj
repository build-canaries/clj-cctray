(defproject cctray-clj "0.1.0-SNAPSHOT"
            :description "XML parser for cctray"
            :dependencies [[org.clojure/clojure "1.6.0"]
                           [midje "1.6.3"]
                           [ring "1.2.2"]
                           [compojure "1.1.8"]
                           [org.clojure/data.json "0.2.4"]]
            :plugins [[lein-ring "0.8.11"]
                      [lein-midje "3.1.1"]
                      [lein-idea "1.0.1"]
                      [lein-shell "0.4.0"]]
            :ring {:handler cctray-clj.app/app :port 9090}
            :main cctray-clj.app
            :jar-name "cctray-clj.jar"
            :uberjar-name "cctray-clj-standalone.jar")
