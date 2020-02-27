package com.bezdjian.trafiklab.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VersionService {

    private String version;

    public VersionService(@Value("${spring.application.version}")
            String version) {
        this.version = version;
    }

    public String getVersion() {
        log.info("***** Getting version: " + version);
        return this.version;
    }
}

