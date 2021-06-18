package com.pulsar.playing.springkubernetespulsar.listener.consumer;

import com.pulsar.playing.springkubernetespulsar.event.TopicPlayingEvent;
import com.pulsar.playing.springkubernetespulsar.listener.common.ClientCommon;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PlayingConsumer  {

    @Autowired
    private ClientCommon clientCommon;

    @EventListener
    @Async
    public void init(TopicPlayingEvent event) throws PulsarClientException {
        Consumer<byte[]> consumer = clientCommon.consumer();
        Message<byte[]> msg = consumer.receive();
        try {
            log.info("****************** Object Event received: {}", event.toString());
            log.info("****************** Message received: {}", new String(msg.getData()));
            consumer.acknowledge(msg);
        } catch (Exception e) {
            consumer.negativeAcknowledge(msg);
        } finally {
            consumer.close();
        }
    }

}
