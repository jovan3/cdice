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

(def players_set '(("player1" '(2 2 2 2 2)) ("player2" '(3 3 3 3 3))))

(deftest test-do-turn
  (testing "should change the last play reference"
    (reset! ia/last-play nil)
    (ia/do-turn players_set 2 6)
    (is (= {:player "player1" :guess {:value 2 :how_many 6}} @ia/last-play))))

