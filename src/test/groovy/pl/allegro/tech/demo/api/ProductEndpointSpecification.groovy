package pl.allegro.tech.demo.api

import pl.allegro.tech.demo.DemoApplicationSpecification
import spock.lang.Stepwise

@Stepwise
class ProductEndpointSpecification extends DemoApplicationSpecification {

    def "should create product"() {
        given:
        def requestBody = [name: 'Produkt', price : [amount: 3, currency: 'PLN'],
                           image: [url: 'https://via.placeholder.com/150'], description: [text: 'Dlugi opis']]
//                           tags: { [name: 'tag1'], [name: 'tag2'] } ]

        when:
        def response = client.post(path: '/api/products', body: requestBody, requestContentType: 'application/json')
        def productId = response.responseData

        then:
        response.status == 201
    }
}