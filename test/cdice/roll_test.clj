(ns cdice.roll-test
  (:require [clojure.test :refer :all]
            [cdice.roll :as roll]))

(deftest test-roll
  (testing "should return a list with length specified with a parameter"
    (is (= 5 (count (roll/roll 5)))))

  (testing "every list member should be between 1 and 6"
    (is (every? #(<= 1 % 6) (roll/roll 100)))))
    
