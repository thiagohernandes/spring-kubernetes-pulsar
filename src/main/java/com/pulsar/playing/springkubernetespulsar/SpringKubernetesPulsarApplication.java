package com.pulsar.playing.springkubernetespulsar;

import com.pulsar.playing.springkubernetespulsar.config.YamlConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@Slf4j
public class SpringKubernetesPulsarApplication implements CommandLineRunner {

    @Autowired
    private YamlConfig yamlConfig;

    public static void main(String[] args) {
        SpringApplication.run(SpringKubernetesPulsarApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Environment: {}", yamlConfig.getEnvironment());
        log.info("Port: {}", yamlConfig.getPort());
    }
}
