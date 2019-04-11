package pl.allegro.tech.demo

import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DemoApplicationSpecification extends Specification {

    @Shared
    def client = new RESTClient("http://localhost:8080")

}