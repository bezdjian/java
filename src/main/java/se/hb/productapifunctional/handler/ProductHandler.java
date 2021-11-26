package se.hb.productapifunctional.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.hb.productapifunctional.model.Product;
import se.hb.productapifunctional.model.ProductEvent;
import se.hb.productapifunctional.repository.ProductRepository;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductRepository repository;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<Product> products = repository.findAll();

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(products, Product.class);
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<Product> product = repository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return product.flatMap(p ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .body(fromValue(p)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Product> productMono = request.bodyToMono(Product.class);

        return productMono.flatMap(product -> saveProduct(product, ServerResponse.status(HttpStatus.CREATED)));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Product> existingProductMono = repository.findById(id);
        Mono<Product> productMono = request.bodyToMono(Product.class);

        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return productMono
                .zipWith(existingProductMono, this::mapProduct)
                .flatMap(product -> saveProduct(product, ServerResponse.ok()))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<Product> productMono = repository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return productMono.flatMap(this::deleteProduct).switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        return ServerResponse.ok().build(repository.deleteAll());
    }

    public Mono<ServerResponse> getProductEvents(ServerRequest request) {
        Flux<ProductEvent> eventFlux = Flux.interval(Duration.ofSeconds(2))
                .map(this::mapProductEvent);

        return ServerResponse.ok()
                .contentType(TEXT_EVENT_STREAM)
                .body(eventFlux, ProductEvent.class);
    }

    private ProductEvent mapProductEvent(Long v) {
        return ProductEvent.builder()
                .eventId(v.toString())
                .eventType("Event Product")
                .build();
    }

    private Product mapProduct(Product product, Product existingProduct) {
        return Product.builder()
                .id(existingProduct.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    private Mono<ServerResponse> saveProduct(Product product, ServerResponse.BodyBuilder response) {
        return response
                .contentType(APPLICATION_JSON)
                .body(repository.save(product), Product.class);
    }

    private Mono<ServerResponse> deleteProduct(Product existingProduct) {
        return ServerResponse.ok()
                .build(repository.delete(existingProduct));
    }
}
