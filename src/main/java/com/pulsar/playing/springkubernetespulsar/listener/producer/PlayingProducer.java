package com.pulsar.playing.springkubernetespulsar.listener.producer;

import com.pulsar.playing.springkubernetespulsar.listener.common.ClientCommon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PlayingProducer {

    private final ClientCommon clientCommon;
    private final ApplicationEventPublisher publisher;

    public void simpleProducer(final String topic, final String message) {
        clientCommon.producer(topic, message);
    }
}
