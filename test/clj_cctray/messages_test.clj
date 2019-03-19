(ns clj-cctray.messages-test
  (:require [clj-cctray.messages :as subject]
            [clojure.test :refer :all]))

(deftest extract-messsages
  (testing "returns the message text in a seq"
    (is (= {:messages ["message-1" "message-2"]} (subject/extract-messages {:content [{:tag      :messages
                                                                                        :content [{:tag   :message
                                                                                                   :attrs {:text "message-1"}}
                                                                                                  {:tag   :message
                                                                                                   :attrs {:text "message-2"}}]}]}))))

  (testing "returns an empty seq if there is no content"
    (is (= {:messages []} (subject/extract-messages {:content nil}))))

  (testing "returns an empty seq if there is no messages tag"
    (is (= {:messages []} (subject/extract-messages {:content [{:tag :something-else}]}))))

  (testing "returns an empty seq if there is a messages tag but it has no content"
    (is (= {:messages []} (subject/extract-messages {:content [{:tag      :messages
                                                                 :content nil}]})))))
