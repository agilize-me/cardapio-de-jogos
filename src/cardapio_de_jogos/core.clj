(ns cardapio-de-jogos.core
  (:gen-class))

(defn escape-string [x]
  (clojure.string/replace x #"^[':\\]" "\\\\$0"))

(defn code-to-json [x]
  (condp #(%1 %2) x
    number?  x
    symbol?  (str \' (name x))
    keyword? (str \: (name x))
    string?  (escape-string x)
    list?    (into [] (cons "list"   (map code-to-json x)))
    vector?  (into [] (cons "vector" (map code-to-json x)))
    set?     (into [] (cons "set"    (map code-to-json x)))
    map?     (into {} (map #(mapv code-to-json %) x))
    (throw (Exception. (format "Unsupported type: %s" (type x))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (do (def gamelist [{:name "The Resistance"
                      :min-player 5
                      :max-player 10}
                     {:name "Dixit"
                      :min-player 3
                      :max-player 12}])
      (doseq [i gamelist] (println (get i :name)))
      (println (code-to-json gamelist))))
