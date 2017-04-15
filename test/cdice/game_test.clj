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
      (is (= {:next :proceed :played {:player "player1" :guess {:value 2 :how_many 3}}} (game/play  {:player "player1" :guess {:value 2 :how_many 2}} {:value 2 :how_many 3} player_sets))))
    (testing "should throw exception if the current guess 'how_many' is less than or equal to the one from the previous guess for the same value"
      (is (thrown? java.lang.AssertionError (game/play {:player "player1" :guess {:value 2 :how_many 3}} {:value 2 :how_many 3} player_sets)))
      (is (thrown? java.lang.AssertionError (game/play {:player "player1" :guess {:value 6 :how_many 10}} {:value 6 :how_many 10} player_sets)))
      (is (thrown? java.lang.AssertionError (game/play {:player "player1" :guess {:value 6 :how_many 10}} {:value 6 :how_many 9} player_sets))))
      

    (testing "and when the current guess value is different than the previous guess value"
      (testing "should throw exception if the current value is smaller than the previous and the current 'how_many' is smaller or equal than the previous"
        (is (thrown? java.lang.AssertionError (game/play {:player "player1" :guess {:value 5 :how_many 5}} {:value 4 :how_many 5} player_sets)))
        (is (thrown? java.lang.AssertionError (game/play {:player "player1" :guess {:value 5 :how_many 5}} {:value 4 :how_many 4} player_sets))))
      (testing "should throw exception if the curent value is larger than the previous and the current 'how_many' is smaller than the previous"
        (is (thrown? java.lang.AssertionError (game/play {:player "player1" :guess {:value 5 :how_many 5}} {:value 6 :how_many 4} player_sets))))
      (testing "should throw exception if the current value is less than the previous and the current 'how_many' is smaller than or equal to the previous"
        (is (thrown? java.lang.AssertionError (game/play {:player "player1" :guess {:value 5 :how_many 5}} {:value 4 :how_many 5} player_sets)))
        (is (thrown? java.lang.AssertionError (game/play {:player "player1" :guess {:value 5 :how_many 5}} {:value 4 :how_many 4} player_sets)))))))
        
   
      

