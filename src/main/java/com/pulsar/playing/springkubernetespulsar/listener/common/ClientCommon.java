package com.pulsar.playing.springkubernetespulsar.listener.common;

import com.pulsar.playing.springkubernetespulsar.event.TopicPlayingEvent;
import com.pulsar.playing.springkubernetespulsar.listener.constants.ClientConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientCommon {

    private final ApplicationEventPublisher publisher;

    public PulsarClient connect() throws PulsarClientException {
        try {
            PulsarClient connection =  PulsarClient.builder()
                    .serviceUrl("pulsar://localhost:6650")
                    .build();
            log.info("Connection Success on Apache Pulsar!");
            return connection;
        } catch (Exception e) {
            log.error("Error connection to Apache Pulsar: {} ", e.getMessage());
            throw new PulsarClientException("Problems on connection - Apache Pulsar!");
        }
    }

    public Consumer<byte[]> consumer() throws PulsarClientException {
        try  {
            Consumer<byte[]> consumer = connect()
                    .newConsumer()
                    .topic(ClientConstants.TOPIC_PLAYING)
                    .subscriptionName(ClientConstants.SUBSCRIPTION_1)
                    .subscribe();
            log.info("Consumer Success Created on Apache Pulsar!");
            return consumer;
        } catch (Exception e) {
            log.error("Error consumer creation to Apache Pulsar: {} ", e.getMessage());
            throw new PulsarClientException("Problems on consumer creation to Apache Pulsar!");
        }
    }

    public void producer(final String topic, final String message) {
        try (Producer<byte[]> producer = connect()
                .newProducer()
                .topic(topic)
                .create()) {
            producer.send(message.getBytes());
            publisher.publishEvent(TopicPlayingEvent.builder()
                    .topic(topic)
                    .value(message)
                    .build());
            log.info("Message success sended: [TOPIC] {} - [MESSAGE] {}", topic, message);
        } catch (Exception e) {
            log.error("Error on simple producer: {}", e.getMessage());
        }
    }


}
