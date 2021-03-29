package org.acme.api.user;

import lombok.extern.slf4j.Slf4j;
import org.acme.client.model.User;
import org.acme.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Path("/users")
@RequestScoped
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class UserResource {
    //http://localhost:8080/q/swagger-ui

    @Inject
    UserService userService;

    @GET
    @Path("/search/{name}")
    public Response search(@PathParam("name") String name) {
        log.info("Searching for user by name {}", name);
        return Response.ok(userService.searchByName(name)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        log.info("Looking for user by ID {}", id);
        return userService.getUserById(id)
                .map(todo -> Response.ok(todo).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    public Response all() {
        //https://quarkus.io/guides/maven-tooling#development-mode
        //https://quarkus.pro/extensions/#data
        return Response.ok(userService.getAllUsers()).build();

    }
}