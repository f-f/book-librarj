(ns book-librarj.events
    (:require [re-frame.core :refer [reg-event-db reg-event-fx dispatch reg-fx]]
              [ajax.core :as ajax]
              [book-librarj.db :refer [default-db]]))

(defonce timeouts (atom {}))

(reg-fx :dispatch-debounce
        (fn [[id event-vec n]]
          (js/clearTimeout (@timeouts id))
          (swap! timeouts assoc id
                 (js/setTimeout (fn []
                                  (dispatch event-vec)
                                  (swap! timeouts dissoc id))
                                n))))

(reg-event-fx
 :initialize-db
 (fn  [{:keys [db]} _]
   {:db default-db
    :http-xhrio {:method          :get
                 :uri             "books"
                 :timeout         8000
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [:good-init]
                 :on-failure      [:bad-http-result]}}))

(reg-event-fx
  :good-init
  (fn [{:keys [db]} [_ books]]
    {:db (assoc db :books books
                   :books-map (->> books
                                   (mapcat #(list (str (:id %)) %))
                                   (apply hash-map)))
     :dispatch-n [[:set-active-panel :books-list]
                  [:set-loading false]]}))

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

(reg-event-db
  :set-loading
  (fn [db [_ val]]
    (assoc db :loading? val)))

(reg-event-fx
  :search-debounced
  (fn [_ [_ searchstring]]
    {:dispatch-debounce [::search [:search searchstring] 500]}))

(reg-event-fx
  :search
  (fn [{:keys [db]} [_ query]]
    (if (empty? query)
      {:db (assoc db :searchstring ""
                     :searching?   false)}
      {:db (assoc db :searchstring query
                     :loading?     true)
       :http-xhrio {:method          :get
                    :uri             "search"
                    :timeout         8000
                    :params          {:q query}
                    :response-format (ajax/json-response-format {:keywords? true})
                    :on-success      [:display-search]
                    :on-failure      [:bad-http-result]}})))

(reg-event-fx
  :display-search
  (fn [{:keys [db]} [_ books]]
    {:db (assoc db :search-list books
                   :searching?  true
                   :loading?    false)}))
