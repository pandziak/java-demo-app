package pl.allegro.tech.demo.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.allegro.tech.demo.infrastructure.products.exceptions.ProductNotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<String> handle(ProductNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        return ResponseEntity.status(status).body("Product not found");
    }
}
