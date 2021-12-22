package com.pulsar.playing.springkubernetespulsar.config;

import com.pulsar.playing.springkubernetespulsar.listener.event.TopicPlayingEvent;
import com.pulsar.playing.springkubernetespulsar.listener.common.ClientCommon;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ClientCommonConfig {

    @Bean
    public ClientCommon<TopicPlayingEvent> clientCommonPojo() {
        return new ClientCommon<>(TopicPlayingEvent.class);
    }
}
