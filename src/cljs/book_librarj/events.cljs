(ns book-librarj.handlers
    (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
              [ajax.core :as ajax]
              [book-librarj.db :refer [default-db]]))

(reg-event-fx
 :initialize-db
 (fn  [{:keys [db]} _]
   {:db default-db
    :http-xhrio {:method          :get
                 :uri             "books"
                 :timeout         8000
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [:good-http-result]
                 :on-failure      [:bad-http-result]}}))

(reg-event-fx
  :good-http-result
  (fn [{:keys [db]} [_ books]]
    {:db (assoc db :books books
                   :books-map (->> books
                                   (mapcat #(list (str (:id %)) %))
                                   (apply hash-map)))
     :dispatch [:set-active-panel :books-list]}))

(reg-event-fx
  :bad-http-result
  (fn [{:keys [db]} _]
    {:db (assoc db :error-text "There was an error while fetching the data.")
     :dispatch [:set-active-panel :error]}))

(reg-event-db
  :set-current-book
  (fn [db [_ book-id]]
    (assoc db :current book-id)))

(reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))
