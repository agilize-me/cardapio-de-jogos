(ns cardapio-de-jogos.core
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes ANY]]))

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

(defn get-games [] (code-to-json [{:name "The Resistance"
                                   :min-player 5
                                   :max-player 10}
                                  {:name "Dixit"
                                   :min-player 3
                                   :max-player 12}]))


(defresource parameter [txt]
             :available-media-types ["text/plain"]
             :handle-ok (fn [_] (format "The text is %s" txt)))

(defroutes app
           (ANY "/foo" [] (resource :available-media-types ["text/html"]
                                    :handle-ok (fn [ctx]
                                                 (format "<html>It's %d milliseconds since the beginning of the epoch."
                                                         (System/currentTimeMillis)))))
           (ANY "/games" [] (resource :available-media-types ["text/html"]
                                    :handle-ok (fn [ctx]
                                                 (format "%s"
                                                         (get-games)))))
           (ANY "/bar/:txt" [txt] (parameter txt)))

(def handler
  (-> app
      wrap-params))

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

(defresource hello-world
             :available-media-types ["text/plain"]
             :handle-ok "Hello, world!")
