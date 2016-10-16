(ns book-librarj.html
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all])
  (:gen-class))

(defn index
  "Returns a string with the html for the index"
  [_]
  (html5 [:head
          [:meta {:charset "utf-8"}]
          [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
          [:meta {:name "viewport"
                  :content "width=device-width, initial-scale=1"}]
          [:meta {:name "description" :content ""}]
          [:meta {:name "author" :content ""}]
          [:title "Book Librarj"]
          (include-css "http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css")
          (include-css "vendor/css/material-design-iconic-font.min.css")
          (include-css "https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css")
          (include-css "vendor/css/re-com.css")
          (include-css "http://fonts.googleapis.com/css?family=Roboto:300,400,500,700,400italic")
          (include-css "http://fonts.googleapis.com/css?family=Roboto+Condensed:400,300")
          (include-css "/css/style.css")]
         [:body
          [:div.jumbotron
           [:div.container
            [:h1 "Book Librarj"]
            [:p "We have some good books hanging around here, grab and read some!"]]]
          [:div#app.container
           [:div.loading
            [:i.fa.fa-4x.fa-circle-o-notch.fa-spin]]]
          (include-js "https://code.jquery.com/jquery-2.1.4.min.js")
          (include-js "https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js")
          (include-js "/js/compiled/app.js")
          [:script "book_librarj.core.init();"]]))