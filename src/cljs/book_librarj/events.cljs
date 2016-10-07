(ns book-librarj.handlers
    (:require [re-frame.core :refer [reg-event-db]]
              [book-librarj.db :as db]))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))
