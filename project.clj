(defproject clj-cctray "2.0.0"
  :description "Clojure parser for cctray.xml"
  :url "https://github.com/build-canaries/clj-cctray"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clojure.java-time "0.3.2"]]
  :javac-options ["-Dclojure.compiler.direct-linking=true"]
  :aliases {"lint"          ["with-profile" "+test" "eastwood"]
            "coverage"      ["with-profile" "+test" "cloverage"]
            "check-updates" ["ancient" ":all"]
            "pre-push"      ["do" "clean," "lint," "test," "coverage"]}
  :repositories [["releases" {:url           "https://clojars.org/repo"
                              :username      :env/clojars_username
                              :password      :env/clojars_password
                              :sign-releases false}]]
  :codox {:output-path "docs"}
  :profiles {:dev {:plugins      [[lein-ancient "0.6.15"]
                                  [jonase/eastwood "0.3.6"]
                                  [lein-cloverage "1.1.2"]
                                  [lein-codox "0.10.7"]]
                   :dependencies [[org.clojure/clojure "1.10.1"]]}}
  :cloverage {:output "target/coverage-reports"
              :junit? true}
  :pom-location "target/")
