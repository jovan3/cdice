(ns cdice.diceset-test
  (:require [clojure.test :refer :all]
            [cdice.diceset :as pset]))

(deftest test-create
  (testing "should throw exception if the list of dice values contains an element outside [1-6]"
    (is (thrown? java.lang.AssertionError (pset/create "player1" '(1 2 7))))
    (is (thrown? java.lang.AssertionError (pset/create "player1" '(0 1 2)))))

  (testing "should throw exception if the player name is empty"
    (is (thrown? java.lang.AssertionError (pset/create "" '(1 2 3))))))

(deftest test-num-of
  (testing "should return the number of dices with a given value"
    (is (= 1 (pset/num-of 5 '(2 3 4 5 6)))))

  (testing "should count 1's as wildcards"
    (is (= 2 (pset/num-of 2 '(1 2)))))

  (testing "should return 0 if no dice has that value"
    (is (= 0 (pset/num-of 3 '(2 4 5 6)))))

  (testing "should count wildcards as matches, even when there are no actual matches"
    (is (= 1 (pset/num-of 4 '(1 2 3 5 6))))))

    


