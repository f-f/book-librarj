(ns book-librarj.views
    (:require [re-frame.core :refer [subscribe dispatch]]
              [reagent.core :as r]
              [re-com.core :as rc]))

;; loading

(defn loading []
  [:div.loading
   [:i.fa.fa-4x.fa-circle-o-notch.fa-spin]])

;; error

(defn error-panel
  ([text]
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
                :body       [:span text]]]])
  ([]
   (let [error-text (subscribe [:error-text])]
     (fn [] [error-panel @error-text]))))

;; search

(defn searchbar []
  (let [m (atom "")]
    (fn []
      [rc/input-text
       :width "100%"
       :class "search-bar"
       :change-on-blur? false
       :model m
       :placeholder "Search for titles..."
       :on-change #(dispatch [:search-debounced %])])))

(defn search-list []
  (let [books (subscribe [:search-list])]
    (fn []
      (if (empty? @books)
        [error-panel "No books found for the current query"]
        [:div
         [:h2 "Search results:"]
         [:div.row
          (for [{:keys [thumbnail id]} @books]
            ^{:key id}
            [:div.col-md-3
             [:a {:href (str "#/book/" id)}
              [:img.img-thumbnail
               {:src thumbnail}]]])]]))))

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
            {:src thumbnail
             :alt title}]]])])))

(defn home-panel []
  (let [loading?   (subscribe [:loading?])
        searching? (subscribe [:searching?])]
    (fn []
      [rc/v-box
       :gap "1em"
       :children [[searchbar]
                  (if @loading?
                    [loading]
                    (if @searching?
                      [search-list]
                      [books-list]))]])))

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

(defn book-panel []
  [rc/v-box
   :gap "1em"
   :children [[link-to-home-page]
              [book-details]]])

;; main

(defmulti  panels identity)
(defmethod panels :books-list   [] [home-panel])
(defmethod panels :book-detail  [] [book-panel])
(defmethod panels :error        [] [error-panel])
(defmethod panels :default      [] [loading])

(defn main-panel []
  (let [active-panel (subscribe [:active-panel])]
    (fn []
      [rc/v-box
       :height "100%"
       :children [[panels @active-panel]]])))
