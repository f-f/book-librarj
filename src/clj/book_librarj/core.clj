(ns book-librarj.core
  (:gen-class)
  (:require
    [system.repl :refer [set-init! start stop reset refresh]]
    [book-librarj.systems :refer [prod-system]]))

(defn -main
  "Start a production system."
  [& args]
  (set-init! #'prod-system)
  (println "Starting Book Librarj...")
  (start))