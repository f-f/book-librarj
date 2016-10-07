(ns book-librarj.systems
  (:gen-class)
  (:require [system.core :refer [defsystem]]
            (system.components
              [http-kit :refer [new-web-server]])
            [book-librarj.handler :refer [app]]))

(defsystem dev-system
           [:web (new-web-server 3000 app)])

(defsystem prod-system
           [:web (new-web-server 3000 app)])
