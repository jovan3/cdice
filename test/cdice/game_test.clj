(ns cdice.game-test
  (:require [clojure.test :refer :all]
            [cdice.game :as game]
            [cdice.diceset :as set]))

(def player_sets (list
                  (set/create "player1" '(1 2 3))
                  (set/create "player2" '(2 3 4))
                  (set/create "player3" '(3 4 5))))

(deftest test-play
  ; Should decide how the game will proceed
  (testing "when playing first guess"
    (testing "should throw exception if the guess is L"
      (is (thrown? java.lang.AssertionError (game/play nil :L player_sets)))))

  (testing "when the guess is :L"
    (testing "should return {:next :all} if previous_play guessed the number dice with a given value"
      (is (= {:next :all} (game/play {:player "player1" :guess {:value 2 :how_many 3}} :L player_sets))))

    (testing "when the previous player guessed that there are"
      (testing "less dice of that value that there actually are in the set, then the current player should lose the difference of guessed and actual dice"
        (is (= {:next :lose :details {:who :current :how_many 1}} (game/play {:player "player1" :guess {:value 2 :how_many 2}} :L player_sets)))
        (is (= {:next :lose :details {:who :current :how_many 3}} (game/play {:player "player1" :guess {:value 3 :how_many 1}} :L player_sets))))
      
      (testing "more dice of that value that there actually are in the set, then the previous player should lose the difference of guessed and actual dice"
        (is (= {:next :lose :details {:who :previous :how_many 1}} (game/play {:player "player1" :guess {:value 2 :how_many 4}} :L player_sets)))
        (is (= {:next :lose :details {:who :previous :how_many 3}} (game/play {:player "player1" :guess {:value 3 :how_many 7}} :L player_sets))))))
  
  (testing "when the guess is other than :L"
    (testing "should return :proceed, the current player and guess"
      
      (is (= {:next :proceed :played {:player "player1" :guess {:value 2 :how_many 3}}} (game/play  {:player "player1" :guess {:value 2 :how_many 2}} {:value 2 :how_many 3} player_sets))))))
