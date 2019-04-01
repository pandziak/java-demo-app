package pl.allegro.tech.demo.infrastructure.products;

import pl.allegro.tech.demo.domain.products.Product;

public interface ProductRepository {

    void save(Product product);

    Product findById(String id);

    Product update(Product product);

    void delete(String id);
}
