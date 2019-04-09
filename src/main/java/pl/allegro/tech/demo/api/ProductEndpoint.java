package pl.allegro.tech.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.allegro.tech.demo.domain.products.*;
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

    @GetMapping
    ProductsResponseDto getProducts() {
        return productFacade.findAll();
    }

    @PostMapping
    ResponseEntity createProduct(@RequestBody ProductRequestDto requestDto) {
        return new ResponseEntity(productFacade.create(requestDto), HttpStatus.CREATED);
    }

    @PutMapping // TODO zmienić na dwa paramsy, id i obiekt?
    ProductResponseDto updateProduct(@RequestBody ProductUpdateDto updateDto) {
        return productFacade.update(updateDto);
    }

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") String id) throws ProductNotFoundException {
        productFacade.delete(id);
    }
}
