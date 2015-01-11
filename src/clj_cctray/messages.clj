(ns clj-cctray.messages)

(defn- add-message [messages message-map]
  (conj messages (get-in message-map [:attrs :text])))

(defn- find-messages [content]
  (first (filter #(= (:tag %) :messages) content)))

(defn- message-content [content]
  (:content (find-messages content)))

(defn extract-messsages
  "Extracts any messages attached to the project as a seq of strings."
  [{:keys [content]}]
  {:messages (reduce add-message [] (message-content content))})
