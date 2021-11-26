package se.hb.productapifunctional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import se.hb.productapifunctional.handler.ProductHandler;
import se.hb.productapifunctional.model.Product;
import se.hb.productapifunctional.repository.ProductRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ProductApiFunctionalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApiFunctionalApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ReactiveMongoOperations operations, ProductRepository productRepository) {
        return args -> {
            Flux<Product> products = createProducts()
                    .flatMap(productRepository::save);

            products.thenMany(productRepository.findAll())
                    .subscribe(System.out::println);

            //With real MongoDB.
            //operations.collectionExists(Product.class)
            //        .flatMap(exists -> exists ? operations.dropCollection(Product.class) :
            //                Mono.just(exists))
            //        .thenMany(v -> operations.createCollection(Product.class))
            //        .thenMany(products)
            //        .thenMany(productRepository.findAll())
            //        .subscribe(System.out::println);
        };
    }

    @Bean
    RouterFunction<ServerResponse> routes(ProductHandler productHandler) {
        return route()
                .path("/products",
                        builder -> builder
                                .nest(accept(APPLICATION_JSON)
                                                .or(contentType(APPLICATION_JSON))
                                                .or(accept(TEXT_EVENT_STREAM)),
                                        nestedBuilder -> nestedBuilder
                                                .GET("/events", productHandler::getProductEvents)
                                                .GET("/{id}", productHandler::getProductById)
                                                .PUT("/{id}", productHandler::update)
                                                .GET(productHandler::getAll)
                                                .POST(productHandler::save)
                                )
                                .DELETE("{id}", productHandler::delete)
                                .DELETE(productHandler::deleteAll)
                ).build();
    }

    //@Bean
    //RouterFunction<ServerResponse> routes(ProductHandler productHandler) {
    //    return route()
    //            .GET("/products/events", accept(TEXT_EVENT_STREAM), productHandler::getProductEvents)
    //            .GET("/products", accept(APPLICATION_JSON), productHandler::getAll)
    //            .POST("/products", contentType(APPLICATION_JSON), productHandler::save)
    //            .DELETE("/products", accept(APPLICATION_JSON), productHandler::deleteAll)
    //            .GET("/products/{id}", accept(APPLICATION_JSON), productHandler::getProductById)
    //            .PUT("/products/{id}", accept(APPLICATION_JSON), productHandler::update)
    //            .DELETE("/products/{id}", accept(APPLICATION_JSON), productHandler::delete)
    //            .build();
    //}

    private Flux<Product> createProducts() {
        return Flux.just(Product.builder().name("Macbook Pro").price(2300.0).build(),
                Product.builder().name("Samsung Note 20").price(20000.0).build(),
                Product.builder().name("Sony").price(1300.0).build());
    }
}
