import javax.enterprise.context.ApplicationScoped;

import org.acme.entity.Todo;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Application implements QuarkusApplication {
    @Override
    public int run(String... args) throws Exception {
        System.out.println("In za Application");
        Todo todo = new Todo();
        todo.title = "Todo1";
        todo.id = "1";
        todo.persist();
    }
}
