(defproject cardapio-de-jogos "0.1.0-SNAPSHOT"
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler cardapio-de-jogos.core/handler}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [liberator "0.14.1"]
                 [compojure "1.3.4"]
                 [ring/ring-core "1.2.1"]]
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main ^:skip-aot cardapio-de-jogos.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
