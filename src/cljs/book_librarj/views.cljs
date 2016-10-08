(ns book-librarj.views
    (:require [re-frame.core :refer [subscribe]]
              [re-com.core :as rc]))


;; home

(defn books-list []
  (let [books (subscribe [:books])]
    (fn []
      [:div.row
       (for [{:keys [title thumbnail id]} @books]
         ^{:key id}
         [:div.col-md-3
          [:a {:href (str "#/book/" id)}
           [:img.img-thumbnail
            {:src thumbnail}]]])])))

(defn home-panel []
  [rc/v-box
   :gap "1em"
   :children [[books-list]]])

;; book details

(defn link-to-home-page []
  [rc/hyperlink-href
   :label "back to Home"
   :href "#/"])

(defn book-image [image]
  [:img.img-thumbnail
   {:src image}])

(defn book-description [{:keys [id title publisher]}]
  [rc/v-box
   :gap "1em"
   :children [[rc/title
               :level :level1
               :label title]
              [:p [:b "Publisher: "] publisher]]])

(defn book-details []
  (let [current (subscribe [:current-book])]
    (fn []
      [:div.row
       [:div.col-md-6 [book-image (:image @current)]]
       [:div.col-md-6 [book-description @current]]])))

(defn about-panel []
  [rc/v-box
   :gap "1em"
   :children [[link-to-home-page]
              [book-details]]])

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
