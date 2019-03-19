(defproject clj-cctray "1.0.2"
  :description "Clojure parser for cctray.xml"
  :url "https://github.com/build-canaries/clj-cctray"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-time "0.15.1"]]
  :javac-options ["-Dclojure.compiler.direct-linking=true"]
  :aliases {"lint"     ["with-profile" "+test" "eastwood"]
            "coverage" ["with-profile" "+test" "cloverage"]
            "pre-push" ["do" "clean," "lint," "coverage"]}
  :repositories [["releases" {:url           "https://clojars.org/repo"
                              :username      :env/clojars_username
                              :password      :env/clojars_password
                              :sign-releases false}]]
  :codox {:output-path "docs"}
  :profiles {:dev {:plugins      [[lein-ancient "0.6.15"]
                                  [jonase/eastwood "0.3.5"]
                                  [lein-cloverage "1.1.0"]]
                   :dependencies [[org.clojure/clojure "1.10.0"]]}}
  :cloverage {:output "target/coverage-reports"
              :junit? true}
  :pom-location "target/")
