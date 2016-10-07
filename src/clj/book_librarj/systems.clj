(ns book-librarj.systems
  (:gen-class)
  (:require [system.core :refer [defsystem]]
            (system.components
              [http-kit :refer [new-web-server]]
              [postgresql :refer [new-postgres-database]])
            [book-librarj.handler :refer [app]]))

(def db-spec
  {:classname   "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "system_test_db"
   :user    "system_test_user"
   :password ""
   :host "127.0.0.1"})

(defsystem dev-system
           [:web (new-web-server 3000 app)])
            ;:db (new-postgres-database db-spec)])

(defsystem prod-system
           [:web (new-web-server 3000 app)
            :db (new-postgres-database db-spec)])
