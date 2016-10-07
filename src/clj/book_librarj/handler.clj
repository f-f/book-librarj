(ns book-librarj.handler
  (:require
    [book-librarj.html :refer [index]]
    [compojure.core :refer [GET defroutes]]
    [compojure.route :as route]
    [system.repl :refer [system]]
    [clojure.java.jdbc :as j]
    [ring.middleware.format :refer [wrap-restful-format]]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defn update-image-link [book]
  (update
    book
    :image
    #(str "http://siili-book-library.s3-eu-west-1.amazonaws.com/" %)))

(defn books
  "Return all the books in the database"
  [_]
  (->> (j/query (:db system) ["select * from books"])
       (map update-image-link)))

(defroutes routes
  (GET "/" [] index)
  (GET "/books" [] books)
  (route/resources "/")
  (route/not-found "404"))

(def app
  (-> routes
      wrap-restful-format
      (wrap-defaults api-defaults)))