(ns cdice.game
  [:require [cdice.turn :as turn]])

(defn not_first_l_guess [previous_play guess]
  (or previous_play (not= guess :L)))

(defn valid_guess [previous_play current_guess]
  (if (and previous_play (= current_guess :L)) true
      (let [previous_guess (:guess previous_play)
            prev_val (:value previous_guess)
            prev_how_many (:how_many previous_guess)
            current_val (:value current_guess)
            current_how_many (:how_many current_guess)]
        (some true?
              [(and (= prev_val current_val) (> current_how_many prev_how_many))
               (and (> current_val prev_val) (= current_how_many prev_how_many))
               (and (> current_val prev_val) (> current_how_many prev_how_many))
               (and (< current_val prev_val) (> current_how_many prev_how_many))]))))

(defn play [previous_play guess player_sets]
  {:pre [(not_first_l_guess previous_play guess),
         (valid_guess previous_play guess)]}
  (if (= guess :L)
    (let [previous_guess (:value (:guess previous_play))
          previous_how_many (:how_many (:guess previous_play))
          guess_result (turn/guess previous_guess previous_how_many player_sets)]
      (cond (= 0 guess_result) {:next :all}
            (< guess_result 0) {:next :lose :details {:who :current :how_many (Math/abs guess_result)}}
            (> guess_result 0) {:next :lose :details {:who :previous :how_many guess_result}}))
    {:next :proceed, :played {:player (:player previous_play), :guess guess}}))
