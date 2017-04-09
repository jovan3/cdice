(ns cdice.diceset)

(defn create [player_name dice]
  {:pre [(and (every? #(<= 1 % 6) dice) (not (empty? player_name)))]}
  (= 1 1))

(defn num-of [value dice]
  (let [freq-value (get (frequencies dice) value 0)
        freq-wildcard (get (frequencies dice) 1 0)]
    (+ freq-value freq-wildcard)))
