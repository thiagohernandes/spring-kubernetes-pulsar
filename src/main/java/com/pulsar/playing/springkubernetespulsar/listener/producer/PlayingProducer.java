package com.pulsar.playing.springkubernetespulsar.listener.producer;

import com.pulsar.playing.springkubernetespulsar.event.TopicPlayingEvent;
import com.pulsar.playing.springkubernetespulsar.listener.common.ClientCommon;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PlayingProducer {

    private final ClientCommon<TopicPlayingEvent> clientCommon;

    @SneakyThrows
    public void producer(final String topic, final TopicPlayingEvent message, final Long messageId) {
        clientCommon.producer(topic, message, messageId);
    }

}
