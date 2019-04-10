package pl.allegro.tech.demo.domain.products;

public interface ProductFacade {

    ProductResponseDto findById(String id);

    ProductsResponseDto findAll(String tag);

    ProductResponseDto create(ProductRequestDto dto);

    ProductResponseDto update(ProductUpdateDto dto);

    void delete(String id);
}
