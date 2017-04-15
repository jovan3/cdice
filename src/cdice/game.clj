(ns cdice.game
  [:require [cdice.turn :as turn]])

(defn play [previous_play guess player_sets]
  {:pre [(or previous_play (not= guess :L))]}
  (if (= guess :L)
    (let [previous_guess (:value (:guess previous_play))
          previous_how_many (:how_many (:guess previous_play))
          guess_result (turn/guess previous_guess previous_how_many player_sets)]
      (cond (= 0 guess_result) {:next :all}
            (< guess_result 0) {:next :lose :details {:who :current :how_many (Math/abs guess_result)}}
            (> guess_result 0) {:next :lose :details {:who :previous :how_many guess_result}}))
    {:next :proceed, :played {:player (:player previous_play), :guess guess}}))
    

