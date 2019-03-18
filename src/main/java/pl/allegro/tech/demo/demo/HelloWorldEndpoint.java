package pl.allegro.tech.demo.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldEndpoint {

    @GetMapping("hello")
    public String hello() {
        return "hello there";
    }
}
