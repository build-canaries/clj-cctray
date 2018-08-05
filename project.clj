(defproject clj-cctray "1.0.1"
  :description "Clojure parser for cctray.xml"
  :url "https://github.com/build-canaries/clj-cctray"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-time "0.14.4"]]
  :javac-options ["-Dclojure.compiler.direct-linking=true"]
  :aliases {"test" ["midje"]
            "lint" ["eastwood"]}
  :repositories [["releases" {:url           "https://clojars.org/repo"
                              :username      :env/clojars_username
                              :password      :env/clojars_password
                              :sign-releases false}]]
  :codox {:output-path "docs"}
  :profiles {:dev {:plugins      [[lein-midje "3.2.1"]
                                  [lein-ancient "0.6.15"]
                                  [jonase/eastwood "0.2.8"]]
                   :dependencies [[org.clojure/clojure "1.9.0"]
                                  [midje "1.9.2"]]}})
