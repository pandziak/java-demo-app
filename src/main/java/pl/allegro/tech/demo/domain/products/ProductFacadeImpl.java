package pl.allegro.tech.demo.domain.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.allegro.tech.demo.domain.products.description.Description;
import pl.allegro.tech.demo.domain.products.image.Image;
import pl.allegro.tech.demo.domain.products.price.Price;
import pl.allegro.tech.demo.domain.products.tag.Tag;
import pl.allegro.tech.demo.infrastructure.products.ProductRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return new ProductResponseDto(product);
    }

    @Override
    public ProductsResponseDto findAll(String tag) {
        List<Product> products = productRepository.findAll(tag);

        List<ProductResponseDto> productsResponse = products.stream()
                .map(ProductResponseDto::new)
                .sorted(Comparator.comparing(ProductResponseDto::getName))
                .collect(Collectors.toList());

        return new ProductsResponseDto(productsResponse);
    }

    @Override
    public ProductResponseDto create(ProductRequestDto dto) {
        if (!dto.isValid()) {
            throw new RuntimeException("Product name cannot be empty");
        }

        Product product = new Product.Builder(
                UUID.randomUUID().toString(),
                dto.getName(),
                new Price(dto.getPrice().getAmount(), dto.getPrice().getCurrency()),
                new Image(dto.getImage().getUrl()),
                new Description(dto.getDescription().getText()),
                LocalDateTime.now())
                .withTags(dto.getTags().stream().map(tag -> new Tag(tag.getName())).collect(Collectors.toList()))
                .build();

        productRepository.save(product);

        return new ProductResponseDto(product);
    }

    @Override
    public ProductResponseDto update(ProductUpdateDto dto) {
        if (!dto.isValid()) {
            throw new RuntimeException("Product is not valid");
        }

        Product oldProduct = productRepository.findById(dto.getId());
        Product updatedProduct = new Product.Builder(
                oldProduct.getId(),
                dto.getName(),
                new Price(dto.getPrice().getAmount(), dto.getPrice().getCurrency()),
                new Image(dto.getImage().getUrl()),
                new Description(dto.getDescription().getText()),
                oldProduct.getCreatedAt())
                .withTags(dto.getTags().stream().map(tag -> new Tag(tag.getName())).collect(Collectors.toList()))
                .build();

        productRepository.update(updatedProduct);

        return new ProductResponseDto(updatedProduct);
    }

    @Override
    public void delete(String id) {
        findById(id); // validate?
        productRepository.delete(id);
    }
}
