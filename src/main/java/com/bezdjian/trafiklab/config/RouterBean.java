package com.bezdjian.trafiklab.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.io.IOException;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Configuration
public class RouterBean {

    @Bean
    public RouterFunction<ServerResponse> indexRouter(
        @Value("classpath:/templates/index.html") final Resource indexHtml) throws IOException {
        log.info("***** IndexHtml {}", indexHtml.getURI().getPath());
        return route(GET("/"), request -> ok()
            .contentType(MediaType.TEXT_HTML)
            .bodyValue(indexHtml));
    }

    @Bean
    public RouterFunction<ServerResponse> staticRouter() {
        return RouterFunctions
            .resources("/static/**", new ClassPathResource("static/"));
    }
}
