(ns book-librarj.handler
  (:require
    [book-librarj.html :refer [index]]
    [compojure.core :refer [GET defroutes]]
    [compojure.route :as route]
    [ring.middleware.format :refer [wrap-restful-format]]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defroutes routes
  (GET "/" [] index)
  (route/resources "/")
  (route/not-found "404"))

(def app
  (-> routes
      wrap-restful-format
      (wrap-defaults api-defaults)))