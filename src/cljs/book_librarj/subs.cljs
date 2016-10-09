(ns book-librarj.subs
    (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :name
 (fn [db]
   (:name db)))

(reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))

(reg-sub
  :error-text
  (fn [db _]
    (:error-text db)))

(reg-sub
  :books
  (fn [db _]
    (:books db)))

(reg-sub
  :current
  (fn [db _]
    (:current db)))

(reg-sub
  :books-map
  (fn [db _]
    (:books-map db)))

(reg-sub
  :searchstring
  (fn [db _]
    (:searchstring db)))

(reg-sub
  :search-list
  (fn [db _]
    (:search-list db)))

(reg-sub
  :current-book
  :<- [:books-map]
  :<- [:current]
  (fn [[books-map current] _]
    (get books-map current)))