(ns clj-cctray.messages-test
  (:require [clj-cctray.messages :as subject]
            [midje.sweet :refer :all]))

(facts "extracts messages"
       (fact "returns the message text in a seq"
             (subject/extract-messsages {:content [{:tag     :messages
                                                    :content [{:tag   :message
                                                               :attrs {:text ..message-1..}}
                                                              {:tag   :message
                                                               :attrs {:text ..message-2..}}]}]}) => {:messages [..message-1.. ..message-2..]})

       (fact "returns an empty seq if there is no content"
             (subject/extract-messsages {:content nil}) => {:messages []})

       (fact "returns an empty seq if there is no messages tag"
             (subject/extract-messsages {:content [{:tag :something-else}]}) => {:messages []})

       (fact "returns an empty seq if there is a messages tag but it has no content"
             (subject/extract-messsages {:content [{:tag     :messages
                                                    :content nil}]}) => {:messages []}))
