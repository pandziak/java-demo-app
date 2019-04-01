package pl.allegro.tech.demo.domain.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.allegro.tech.demo.infrastructure.products.ProductRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class ProductFacadeImpl implements ProductFacade {

    private final ProductRepository productRepository;

    @Autowired
    public ProductFacadeImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto findById(String id) {
        Product product = productRepository.findById(id);
        return new ProductResponseDto(product.getId(), product.getName());
    }

    @Override
    public ProductResponseDto create(ProductRequestDto dto) {
        if (!dto.isValid()) {
            throw new RuntimeException("Product name cannot be empty");
        }

        Product product = new Product(
                UUID.randomUUID().toString(),
                dto.getName(),
                LocalDateTime.now());

        productRepository.save(product);

        return new ProductResponseDto(product.getId(), product.getName());
    }

    @Override
    public ProductResponseDto update(ProductUpdateDto dto) {
        if (!dto.isValid()) {
            throw new RuntimeException("Product is not valid");
        }

        Product oldProduct = productRepository.findById(dto.getId());
        Product updatedProduct = Product.copy(oldProduct, dto);

        productRepository.update(updatedProduct);

        return new ProductResponseDto(updatedProduct.getId(), updatedProduct.getName());
    }

    @Override
    public void delete(String id) {
        findById(id); // validate?
        productRepository.delete(id);
    }
}
