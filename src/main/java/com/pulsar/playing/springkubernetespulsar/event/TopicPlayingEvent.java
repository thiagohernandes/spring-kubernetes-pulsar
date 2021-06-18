package com.pulsar.playing.springkubernetespulsar.event;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class TopicPlayingEvent {

    private String topic;
    private String value;

}
