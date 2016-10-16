(ns book-librarj.systems
  (:gen-class)
  (:require [system.core :refer [defsystem]]
            (system.components
              [http-kit :refer [new-web-server]]
              [postgres :refer [new-postgres-database]])
            [environ.core :refer [env]]
            [book-librarj.handler :refer [app]]))

(defsystem dev-system
           [:web (new-web-server 3000 app)
            :db (new-postgres-database {:dbtype "postgresql"
                                        :dbname (env :db-name)
                                        :host (env :db-host)
                                        :port (env :db-port)
                                        :user (env :db-user)
                                        :password (env :db-pass)})])

(defsystem prod-system
           [:web (new-web-server 3000 app)
            :db (new-postgres-database {:dbtype "postgresql"
                                        :dbname (env :db-name)
                                        :host (env :db-host)
                                        :port (env :db-port)
                                        :user (env :db-user)
                                        :password (env :db-pass)})])