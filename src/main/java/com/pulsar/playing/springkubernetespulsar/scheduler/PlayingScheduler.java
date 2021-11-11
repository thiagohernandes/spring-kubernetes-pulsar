package com.pulsar.playing.springkubernetespulsar.scheduler;

import com.pulsar.playing.springkubernetespulsar.event.TopicPlayingEvent;
import com.pulsar.playing.springkubernetespulsar.listener.constants.ClientConstants;
import com.pulsar.playing.springkubernetespulsar.listener.producer.PlayingProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class PlayingScheduler {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final PlayingProducer playingProducer;

    @Scheduled(fixedRate = 5000)
    public void scheduler() {
        playingProducer.simpleProducer(ClientConstants.TOPIC_PLAYING, TopicPlayingEvent.builder()
                .topic(ClientConstants.TOPIC_PLAYING)
                .value(dateTimeFormatter.format(LocalDateTime.now()))
            .build());
    }

}
