package com.bezdjian.trafiklab.controller;

import com.bezdjian.trafiklab.model.VersionModel;
import com.bezdjian.trafiklab.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @GetMapping("/version")
    public Mono<ResponseEntity<VersionModel>> getVersion() {
        return versionService.getVersion()
            .map(ResponseEntity::ok);
    }
}