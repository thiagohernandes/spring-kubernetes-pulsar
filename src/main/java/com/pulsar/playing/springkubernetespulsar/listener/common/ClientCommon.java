package com.pulsar.playing.springkubernetespulsar.listener.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Reader;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;

import java.io.IOException;

@Slf4j
public class ClientCommon<T> {

    private final Class<T> tClass;

    public ClientCommon(final Class<T> tClass) { this.tClass = tClass; }

    private PulsarClient connect() throws PulsarClientException {
        try {
            return PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();
        } catch (PulsarClientException e) {
            log.error("Error connection to Apache Pulsar: {} ", e.getMessage());
            throw e;
        }
    }

    public void producer(final String topic, final T message)
        throws PulsarClientException {
        PulsarClient client = connect();
        try (client; Producer<T> producer = client
            .newProducer(Schema.JSON(tClass))
            .topic(topic)
            .create()) {
            producer.newMessage()
                .value(message).send();
            log.info("---> Message sync success on send/publish: [TOPIC] {} - [MESSAGE] {}", topic, message);
        } catch (PulsarClientException e) {
            log.error("Error on producer sync: {}", e.getMessage());
            throw e;
        }
    }

    public void consumer(final String consumerName, final String topicName, final String subscriptionName,
        final SubscriptionType subscriptionType) throws PulsarClientException {
        try {
            PulsarClient client = connect();
            final ConsumerBuilder<T> consumerBuilder = client.newConsumer(Schema.JSON(tClass))
                .topic(topicName)
                .subscriptionName(subscriptionName).messageListener((consumer, message) -> {
                    try {
                        consumer.acknowledge(message);
                    } catch (PulsarClientException e) {
                        log.error("Error on ack {}", message, e);
                    }
                    log.info("Consumer sync {} - Message value ->>>> {}",
                        consumer.getConsumerName(), message.getValue());
                })
                .subscriptionType(subscriptionType);
            consumerBuilder
                .consumerName(consumerName)
                .subscribe();
        } catch (PulsarClientException e) {
            log.error("Error on consumer sync: {} - topic {} - subscriptionName {} - error -> {} ",
                consumerName, topicName, subscriptionName, e.getMessage());
            throw e;
        }
    }

    public void producerAsync(final String topic, final T message)
        throws PulsarClientException {
        PulsarClient client = connect();
        try (client; Producer<T> producer = client
            .newProducer(Schema.JSON(tClass))
            .topic(topic)
            .create()) {
            producer.newMessage()
                .value(message)
                .sendAsync()
                .thenAccept(msg -> log.info("Message id {} received", msg))
                .exceptionally(ex -> {
                    log.error("Error on accept producer async, {}", ex.getMessage(), ex);
                    return null;
                });
            log.info("---> Message async success on send/publish: [TOPIC] {} - [MESSAGE] {}", topic, message);
        } catch (PulsarClientException e) {
            log.error("Error on producer async: {}", e.getMessage());
            throw e;
        }
    }

    public void consumerAsync(final String consumerName, final String topicName, final String subscriptionName,
        final SubscriptionType subscriptionType) throws PulsarClientException {
        try {
            PulsarClient client = connect();
            final ConsumerBuilder<T> consumerBuilder = client.newConsumer(Schema.JSON(tClass))
                .topic(topicName)
                .subscriptionName(subscriptionName).messageListener((consumer, message) -> {
                    consumer.receiveAsync();
                    log.info("Consumer async {} - Message value ->>>> {}",
                        consumer.getConsumerName(), message.getValue());
                })
                .subscriptionType(subscriptionType);
            consumerBuilder
                .consumerName(consumerName)
                .subscribeAsync();
        } catch (PulsarClientException e) {
            log.error("Error on consumer async: {} - topic {} - subscriptionName {} - error -> {} ",
                consumerName, topicName, subscriptionName, e.getMessage());
            throw e;
        }
    }

    public void reader(final String topicName, final MessageId messageId)
        throws IOException {
        PulsarClient client = connect();
        try (Reader<T> reader = client.newReader(Schema.JSON(tClass))
            .topic(topicName)
            .startMessageId(messageId)
            .create()) {
            while (reader.hasMessageAvailable()) {
                Message<T> message = reader.readNext();
                log.info("------- Reading message id: {}", message.getValue());
            }
        } catch (PulsarClientException e) {
            log.error("Error reader on topic {}  - error -> {} ",
                topicName, e.getMessage());
            throw e;
        }
    }

}
