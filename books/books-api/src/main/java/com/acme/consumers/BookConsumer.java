package com.acme.consumers;

import com.kumuluz.ee.streaming.common.annotations.StreamListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookConsumer {

    private boolean recievedMessage = false;

    @StreamListener(topics = {"book"})
    public void onMessage(ConsumerRecord<String, String> record) {
        if (record.value().equals("add")) {
            recievedMessage = true;
        }
    }

    public boolean hasRecievedMessage() {
        if (this.recievedMessage) {
            this.recievedMessage = false;
            return true;
        } else {
            return false;
        }
    }
}
