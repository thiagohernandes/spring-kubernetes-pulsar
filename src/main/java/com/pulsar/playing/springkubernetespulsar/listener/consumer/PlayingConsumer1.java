package com.pulsar.playing.springkubernetespulsar.listener.consumer;

import com.pulsar.playing.springkubernetespulsar.listener.event.TopicPlayingEvent;
import com.pulsar.playing.springkubernetespulsar.listener.common.ClientCommon;
import com.pulsar.playing.springkubernetespulsar.listener.constants.ClientConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class PlayingConsumer1 implements InitializingBean {

    private final ClientCommon<TopicPlayingEvent> clientCommon;

    @Override
    public void afterPropertiesSet() throws Exception {
        clientCommon.consumerAsync(ClientConstants.CONSUMER_1,
            ClientConstants.MY_TOPIC_D,
            ClientConstants.SUBSCRIPTION_2,
            SubscriptionType.Exclusive);
    }
}
