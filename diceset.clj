(ns cdice.player_set)

(defn create [player_name dice]
  {:pre [(every? #(<= 1 % 6) dice)]})
