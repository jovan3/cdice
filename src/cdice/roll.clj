(ns cdice.roll)

(defn roll [number-of-dice]
  (take number-of-dice (repeatedly #(+ 1 (rand-int 6)))))
