package com.bezdjian.trafiklab.service;

import com.bezdjian.trafiklab.model.VersionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class VersionService {

    private final String version;

    public VersionService(@Value("${spring.application.version}")
                          String version) {
        this.version = version;
    }

    public Mono<VersionModel> getVersion() {
        log.info("***** Getting version: " + version);
        return Mono.just(VersionModel.builder()
            .version(this.version)
            .build());
    }
}