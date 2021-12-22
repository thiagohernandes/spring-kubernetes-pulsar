package com.pulsar.playing.springkubernetespulsar.scheduler;

import com.pulsar.playing.springkubernetespulsar.listener.event.TopicPlayingEvent;
import com.pulsar.playing.springkubernetespulsar.listener.common.ClientCommon;
import com.pulsar.playing.springkubernetespulsar.listener.constants.ClientConstants;
import com.pulsar.playing.springkubernetespulsar.listener.producer.PlayingProducer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class PlayingScheduler1 {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final PlayingProducer playingProducer;

    @SneakyThrows
    @Scheduled(fixedRate = 5000)
    public void scheduler() {
        final String topic = ClientConstants.MY_TOPIC_D;
        playingProducer.producer(topic,
            TopicPlayingEvent.builder()
                .topic(topic)
                .value(dateTimeFormatter.format(LocalDateTime.now()))
                .build());
    }

}
