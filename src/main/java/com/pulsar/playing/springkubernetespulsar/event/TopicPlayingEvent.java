package com.pulsar.playing.springkubernetespulsar.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class TopicPlayingEvent {

    private String topic;
    private String value;

    public TopicPlayingEvent() {
    }

}
