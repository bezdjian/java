package se.hb.webclientproductapi;

import reactor.core.scheduler.Schedulers;
import se.hb.webclientproductapi.api.WebClientApi;

public class Application {

    public static void main(String[] args) {
        WebClientApi api = new WebClientApi();

        api.postNewProduct()
                .doOnError(error -> System.out.println("Error!: " + error.getMessage()))
                .thenMany(api.getAllProducts())
                .take(1)
                .flatMap(p -> api.updateProduct(p.getId(), "Clean Code v2", 299.0))
                .flatMap(p -> api.deleteProduct(p.getId()))
                .thenMany(api.getAllProducts())
                .thenMany(api.getProductEvents())
                .subscribeOn(Schedulers.newSingle("myThread")) // <-- none daemon thread
                .subscribe(System.out::println);

        //try {
        //    Thread.sleep(5000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
    }
}
