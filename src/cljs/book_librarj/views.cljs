(ns book-librarj.views
    (:require [re-frame.core :refer [subscribe]]
              [re-com.core :as rc]))


;; home

(defn home-title []
  (let [name (subscribe [:name])]
    (fn []
      [rc/title
       :label (str "Hello from " @name ". This is the Home Page.")
       :level :level1])))

(defn link-to-about-page []
  [rc/hyperlink-href
   :label "go to About Page"
   :href "#/about"])

(defn books-list []
  (let [books (subscribe [:books])]
    (fn []
      [:div.row
       (for [{:keys [title image id]} @books]
         ^{:key id}
         [:div.col-md-3
          [:a {:href (str "#/book/" id)}
           [:img.img-thumbnail
            {:src (str "http://siili-book-library.s3-eu-west-1.amazonaws.com/"
                       image)}]]])])))

(defn home-panel []
  [rc/v-box
   :gap "1em"
   :children [[books-list]]])


;; about

(defn about-title []
  [rc/title
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page []
  [rc/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [rc/v-box
   :gap "1em"
   :children [[about-title] [link-to-home-page]]])

;; error

(defn error-panel []
  (let [error-text (subscribe [:error-text])]
    (fn []
      [rc/v-box
       :gap "1em"
       :children [[rc/alert-box
                   :alert-type :none
                   :style {:color             "#333"
                           :background-color  "rgba(255, 0, 0, 0.1)"
                           :border-top        "none"
                           :border-right      "none"
                           :border-bottom     "none"
                           :border-left       "4px solid rgba(255, 0, 0, 0.8)"
                           :border-radius     "0px"}
                   :heading    "Error."
                   :body       [:span @error-text]]]])))

;; main

(defmulti  panels identity)
(defmethod panels :books-list  [] [home-panel])
(defmethod panels :book-detail [] [about-panel])
(defmethod panels :error       [] [error-panel])
(defmethod panels :default     [] [:div "TODO"])

(defn show-panel
  [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (subscribe [:active-panel])]
    (fn []
      [rc/v-box
       :height "100%"
       :children [[panels @active-panel]]])))
