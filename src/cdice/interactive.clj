(ns cdice.interactive
  (:require [cdice.diceset :as set]
            [cdice.roll :as roll]
            [cdice.turn :as turn]))

(def last-play (atom nil))
(def players_set (atom nil))

(defn enter-player-names []
  (println "How many players: ")
  (loop [players (read-string (read-line)) current_player 0 players_list '()]
    (if (= current_player players)
      players_list
      (recur players (inc current_player) (conj players_list (read-line))))))

(defn init-players [players_list]
  {:pre [(and (some? players_list) (not (empty? players_list)))]}
  (map (fn [player] (list player (roll/roll 5))) players_list))
