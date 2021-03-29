package org.acme.client;

import org.acme.client.model.User;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Singleton
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com")
public interface UserClient {

    @GET
    List<User> getAllUsers();

    @GET
    @Path("/{id}")
    Optional<User> getUserById(@PathParam("id") Long id);
}
