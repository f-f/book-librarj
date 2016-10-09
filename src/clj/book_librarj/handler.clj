(ns book-librarj.handler
  (:require
    [book-librarj.html :refer [index]]
    [compojure.core :refer [GET defroutes]]
    [compojure.route :as route]
    [system.repl :refer [system]]
    [clojure.java.jdbc :as j]
    [ring.util.response :refer [response content-type charset]]
    [ring.middleware.format :refer [wrap-restful-format]]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defn update-image-link [book]
  (let [base-url "http://siili-book-library.s3-eu-west-1.amazonaws.com/"
        image-name (:image book)
        image-url (str base-url image-name)
        thumbnail-url (str base-url "thumbs/" image-name)]
    (assoc book
      :image image-url
      :thumbnail thumbnail-url)))

(defn get-books
  "Return all the books in the database"
  [_]
  (->> (j/query (:db system) ["select * from books"])
       (map update-image-link)))

(defn search
  "Search in the titles for the provided keyword"
  [s]
  (->> (j/query
         (:db system)
         ["SELECT * FROM books WHERE to_tsvector(title) @@ to_tsquery(?);" s])
       (map update-image-link)))

(defroutes routes
  (GET "/" [] index)
  (GET "/books" [] get-books)
  (GET "/search/:query" [query] (-> (search query) response))
  (route/resources "/")
  (route/not-found "404"))

(def app
  (-> routes
      wrap-restful-format
      (wrap-defaults api-defaults)))