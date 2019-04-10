package pl.allegro.tech.demo.infrastructure.products;

import org.springframework.stereotype.Repository;
import pl.allegro.tech.demo.domain.products.Product;
import pl.allegro.tech.demo.domain.products.tag.Tag;
import pl.allegro.tech.demo.infrastructure.products.exceptions.ProductNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void save(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Product findById(String id) {
        Product product = products.get(id);

        if (Objects.isNull(product)) {
            throw new ProductNotFoundException();
        }

        return product;
    }

    @Override
    public List<Product> findAll(String tag) {
        return new ArrayList<>(
                products.values()
                        .stream()
                        .filter(p -> isNull(tag) || p.getTags().contains(new Tag(tag)))
                        .collect(Collectors.toList()));
    }

    @Override
    public Product update(Product product) {
        return products.replace(product.getId(), product);
    }

    @Override
    public void delete(String id) {
        products.remove(id);
    }
}
