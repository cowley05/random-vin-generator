(ns random-vin-generator.core
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]))

(s/conform even? 1002)

(s/def ::german-prefix #{\W})

(s/def ::valid-model-year
  #{\A \B \C \D \E \F \G \H \J \K \L \M \N \P \R \S \T \U \V \W \X \Y
    \1 \2 \3 \4 \5 \6 \7 \8 \9})

(s/def ::valid-letters
  #{\A \B \C \D \E \F \G \H \J \K \L \M \N \P \R \S \T \U \V \W \X \Y \Z})

(s/def ::valid-digits #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9})

(s/conform ::valid-letters \B)
(s/conform ::valid-letters \O)
(s/conform ::valid-digits \3)
(s/conform ::valid-digits \A)

(s/def ::valid-letter-or-digit
  (s/or :digit ::valid-digits :letter ::valid-letters))

(s/conform ::valid-letter-or-digit \B)
(s/conform ::valid-letter-or-digit \O)
(s/conform ::valid-letter-or-digit \3)
(s/conform ::valid-letter-or-digit \A)

(s/def ::valid-one-vin (s/cat :1 ::valid-letter-or-digit))

(s/def ::valid-vin
  (s/cat
   :1 ::german-prefix
   :2 ::valid-letter-or-digit
   :3 ::valid-letter-or-digit
   :4 ::valid-letter-or-digit
   :5 ::valid-letter-or-digit
   :6 ::valid-letter-or-digit
   :7 ::valid-letter-or-digit
   :8 ::valid-letter-or-digit
   :9 ::valid-letter-or-digit
   :10 ::valid-model-year
   :11 ::valid-letter-or-digit
   :12 ::valid-letter-or-digit
   :13 ::valid-letter-or-digit
   :14 ::valid-letter-or-digit
   :15 ::valid-letter-or-digit
   :16 ::valid-letter-or-digit
   :17 ::valid-letter-or-digit))

(s/conform ::valid-vin (map identity "W1234567891234567"))
(s/conform ::valid-vin (map identity "0123456789123456"))
(s/conform ::valid-vin (map identity "012345678912345678"))

(defn one-random-vin []
  (apply str (gen/generate (s/gen ::valid-vin))))

(dorun
 (take 5000 (repeatedly #(spit "/tmp/vins.txt" (str (one-random-vin) " ")
                               :append true))))

