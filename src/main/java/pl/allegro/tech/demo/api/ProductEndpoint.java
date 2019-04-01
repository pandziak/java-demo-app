package pl.allegro.tech.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.allegro.tech.demo.domain.products.ProductFacade;
import pl.allegro.tech.demo.domain.products.ProductRequestDto;
import pl.allegro.tech.demo.domain.products.ProductResponseDto;
import pl.allegro.tech.demo.domain.products.ProductUpdateDto;
import pl.allegro.tech.demo.infrastructure.products.exceptions.ProductNotFoundException;

@RestController
@RequestMapping("/api/products")
class ProductEndpoint {

    private final ProductFacade productFacade;

    @Autowired
    ProductEndpoint(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("/{id}")
    ProductResponseDto getProduct(@PathVariable("id") String id) {
        return productFacade.findById(id);
    }

    @PostMapping
    ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto) {
        return productFacade.create(requestDto);
    }

    @PutMapping
    ProductResponseDto updateProduct(@RequestBody ProductUpdateDto updateDto) {
        return productFacade.update(updateDto);
    }

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") String id) throws ProductNotFoundException {
        productFacade.delete(id);
    }
}
