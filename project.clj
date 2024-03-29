(defproject clj-cctray "2.1.1"
  :description "Clojure parser for CCTray XML"
  :url "https://github.com/build-canaries/clj-cctray"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clojure.java-time "0.3.3"]]
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
  :profiles {:dev {:plugins      [[lein-ancient "0.7.0"]
                                  [jonase/eastwood "0.9.9"]
                                  [lein-cloverage "1.2.2"]
                                  [lein-codox "0.10.7"]]
                   :dependencies [[org.clojure/clojure "1.10.3"]]}}
  :cloverage {:output "target/coverage-reports"
              :junit? true}
  :pom-location "target/")
