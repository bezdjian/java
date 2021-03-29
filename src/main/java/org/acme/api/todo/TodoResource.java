package org.acme.api.todo;

import lombok.extern.slf4j.Slf4j;
import org.acme.entity.Todo;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;

@Path("/todos")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
@Slf4j
public class TodoResource {

    private final Random random = new Random();

    //http://localhost:8080/q/swagger-ui
    @GET
    @Path("/search/{title}")
    public Response search(@PathParam("title") String title) {
        log.info("Searching for todo by title {}", title);
        return Response.ok(Todo.search(title).toString()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        log.info("Looking for todo by ID {}", id);
        return Todo.findByIdOptional(id)
                .map(todo -> Response.ok(todo).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Transactional
    public Response all() {
        //https://quarkus.io/guides/maven-tooling#development-mode
        //https://quarkus.pro/extensions/#data

        log.info("Adding a todo..");
        Todo todo = Todo.builder()
                .title("Todo-" + random.nextInt(500))
                .build();
        todo.persist();
        List<Todo> todos = Todo.listAll();
        // todos.findAll();
        return Response.ok(todos.toString()).build();
    }
}