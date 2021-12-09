package se.hb.webclientproductapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.hb.webclientproductapi.model.Product;
import se.hb.webclientproductapi.model.ProductEvent;

public class WebClientApi {

    private static final String SERVICE_BASE_URL = "http://localhost:8080/products";
    private final WebClient webClient;

    public WebClientApi() {
        this.webClient = WebClient.builder()
                .baseUrl(SERVICE_BASE_URL)
                .build();
    }

    public Mono<ResponseEntity<Product>> postNewProduct() {
        Product product = Product.builder()
                .name("Clean Code")
                .price(199.0)
                .build();

        return webClient
                .post()
                .body(Mono.just(product), Product.class)
                .exchangeToMono(response -> response.toEntity(Product.class))
                .doOnSuccess(response -> System.out.println("** Posted with status " + response.getStatusCode()));
    }

    public Flux<Product> getAllProducts() {
        return webClient
                .get()
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(o -> System.out.println("GET Retrieved: " + o));
    }

    public Mono<Product> updateProduct(String id, String name, double price) {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .build();

        return webClient
                .put()
                .uri("/{id}", id)
                .body(Mono.just(product), Product.class)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnSuccess(p -> System.out.println("PUT Product: " + p));
    }

    public Mono<Void> deleteProduct(String id) {
        return webClient
                .delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(p -> System.out.println("Delete Product: " + p));
    }

    public Flux<ProductEvent> getProductEvents() {
        return webClient
                .get()
                .uri("/events")
                .retrieve()
                .bodyToFlux(ProductEvent.class);
    }
}
