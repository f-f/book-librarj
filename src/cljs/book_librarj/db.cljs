(ns book-librarj.db)

(def default-db
  {:loading?     true
   :searching?   false
   :error-text?  ""
   :books        []
   :books-map    {}
   :current      nil
   :searchstring ""
   :search-list  []})

