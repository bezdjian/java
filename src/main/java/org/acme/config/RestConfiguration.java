package org.acme.config;

import org.acme.client.UserClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriBuilder;

import static org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder.PROPERTY_PROXY_HOST;
import static org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder.PROPERTY_PROXY_PORT;
import static org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder.PROPERTY_PROXY_SCHEME;

@ApplicationScoped
public class RestConfiguration {

    public UserClient userClient() {
        return RestClientBuilder.newBuilder()
                .baseUri(UriBuilder.fromUri("https://jsonplaceholder.typicode.com").build())
                .property(PROPERTY_PROXY_HOST, "trend3.sbab.ad")
                .property(PROPERTY_PROXY_PORT, 8080)
                .property(PROPERTY_PROXY_SCHEME, "http")
                .build(UserClient.class);
    }
}