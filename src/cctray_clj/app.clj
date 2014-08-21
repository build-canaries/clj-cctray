(ns cctray-clj.app
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as jetty]
            [compojure.response :as response]
            [compojure.core :refer :all]))

(def hello-world "hello world")

(defroutes main-routes
           (GET "/" [] hello-world))

(def app (handler/site main-routes))

(defn -main [& args] (jetty/run-jetty app {:port 9090 :join? false}))
