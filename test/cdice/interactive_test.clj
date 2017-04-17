(ns cdice.interactive-test
  (:require [clojure.test :refer :all]
            [cdice.interactive :as ia]))

(deftest test-init-players
  (testing "should throw exception if nil or empty list of player provided"
    (is (thrown? java.lang.AssertionError (ia/init-players nil)))
    (is (thrown? java.lang.AssertionError (ia/init-players []))))

  (testing "should return a vector of maps {:player _ :diceset _ }"
    (is (every? #(first %) (ia/init-players '("player1" "player2"))))
    (is (every? #(= 5 (count (second %))) (ia/init-players '("pl1" "pl2" "pl3"))))))

(deftest test-do-turn
  (testing "should change the last play reference"
    (is (= nil @ia/last-play))))
