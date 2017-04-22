(ns cdice.interactive-test
  (:require [clojure.test :refer :all]
            [cdice.interactive :as ia]))

(defn init-players-fixture [f]
  (reset! ia/players_dice_remain {})
  (f))

(use-fixtures :each init-players-fixture)

(deftest test-init-players
  (testing "should throw exception if nil or empty list of player provided"
    (is (thrown? java.lang.AssertionError (ia/init-players nil)))
    (is (thrown? java.lang.AssertionError (ia/init-players []))))

  (testing "should return a vector of maps {:player _ :diceset _ }"
    (is (every? #(first %) (ia/init-players '("player1" "player2"))))
    (is (every? #(= 5 (count (second %))) (ia/init-players '("pl1" "pl2" "pl3"))))))

(deftest test-init-players-state
  (testing "should set the players_dice_remain atom"
    (ia/init-players '("pl1" "pl2" "pl3"))
    (is (= (:pl1 @ia/players_dice_remain) 5))
    (is (= (:pl2 @ia/players_dice_remain) 5))
    (is (= (:pl3 @ia/players_dice_remain) 5))
    (is (= (:pl4 @ia/players_dice_remain) nil))))

(def players_set '(("player1" (list 2 2 2 2 2)) ("player2" (list 3 3 3 3 3))))

(deftest test-do-turn
  (testing "should change the last play reference"
    (reset! ia/last-play {:player "player1" :guess {:value 2 :how_many 6}})
    (ia/init-players '("player1" "player2"))
    (let [turn_result (ia/do-turn players_set :L 6)]
      (is (= {:player "player1" :guess {:value 2 :how_many 6}} @ia/last-play))
      (is (= (:player1 @ia/players_dice_remain) 4))
      (is (= (:player2 @ia/players_dice_remain) 5)))))

(deftest get-name-for-turn
  (testing "should get the name of the current player if turn is :current"
    (is (= "player1" (ia/get-name-for-turn :current players_set))))
  (testing "should get the name of the previous player if turn is :previous"
    (reset! ia/last-play {:player "player3"})
    (is (= "player3" (ia/get-name-for-turn :previous players_set)))))

(deftest lose-dice
  (testing "should change the number of remaining dice for a player"
    (ia/init-players '("pl1" "pl2" "pl3"))
    (ia/lose-dice "pl1" 1)
    (is (= (:pl1 @ia/players_dice_remain) 4))
    (is (= (:pl2 @ia/players_dice_remain) 5))
    (is (= (:pl3 @ia/players_dice_remain) 5))))
    
    

