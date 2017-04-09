(ns cdice.turn
  (:require [cdice.diceset :as set]))

(defn guess [value guess all_sets]
  (- guess (apply + (map #(set/num-of value (second %)) all_sets))))
