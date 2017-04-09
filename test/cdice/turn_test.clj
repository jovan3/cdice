(ns cdice.turn-test
  (:require [clojure.test :refer :all]
            [cdice.diceset :as set]
            [cdice.turn :as turn]))

(deftest test-guess
  (testing "should return the difference between the number of dices with a given value among all the players and the guess"
    (let [player1_set (set/create "player1" '(1 2 3))
          player2_set (set/create "player2" '(2 3 4))
          player3_set (set/create "player3" '(3 4 5))
          all_sets (list player1_set player2_set player3_set)]
      (is (= 0 (turn/guess 2 3 all_sets)))
      (is (= 0 (turn/guess 3 4 all_sets)))
      (is (= 0 (turn/guess 4 3 all_sets)))
      (is (= 0 (turn/guess 5 2 all_sets)))
      (is (= 0 (turn/guess 6 1 all_sets)))
      
      (is (= 1 (turn/guess 5 3 all_sets)))
      
      (is (= -1 (turn/guess 5 1 all_sets))))))
