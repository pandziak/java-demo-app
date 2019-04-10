package pl.allegro.tech.demo.infrastructure.products;

import pl.allegro.tech.demo.domain.products.Product;

import java.util.List;

public interface ProductRepository {

    void save(Product product);

    Product findById(String id);

    List<Product> findAll(String tag);

    Product update(Product product);

    void delete(String id);
}
