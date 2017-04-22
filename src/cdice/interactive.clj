(ns cdice.interactive
  (:require [cdice.diceset :as set]
            [cdice.roll :as roll]
            [cdice.turn :as turn]
            [cdice.game :as game]))

(def last-play (atom nil))
(def players_dice_remain (atom {}))

(defn enter-player-names []
  (println "How many players: ")
  (loop [players (read-string (read-line)) current_player 0 players_list '()]
    (if (= current_player players)
      players_list
      (recur players (inc current_player) (conj players_list (read-line))))))

(defn init-players [players_list]
  {:pre [(and (some? players_list) (not (empty? players_list)))]}
  (doseq [player players_list]
    (swap! players_dice_remain assoc-in [(keyword player)] 5))
  (map #(list % (roll/roll 5)) players_list))

(defn make-last-play [player_name value how_many]
  {:player player_name :guess {:value value :how_many how_many}})

(defn make-guess [value how_many]
  (if (= value :L)
    :L
    {:value value :how_many how_many}))

(defn lose-dice [player_name how_many]
  (let [current_player_dice ((keyword player_name) @players_dice_remain)]
    (swap! players_dice_remain assoc-in [(keyword player_name)] (- current_player_dice how_many))))

(defn get-name-for-turn [turn players_set]
  (if (= turn :current)
    (first (first players_set))
    (:player @last-play)))

(defn do-turn [players_set value how_many]
  (let [player (first players_set)
        player_name (first player)]
    (let [play_result (game/play @last-play (make-guess value how_many) players_set)
          next (:next play_result)]
      (cond (= next :lose) (lose-dice (get-name-for-turn (:who (:details play_result)) players_set) (:how_many (:details play_result)))))))
            
            
            
           
      
  
;    (reset! last-play (make-last-play player_name value how_many))))
      
    

