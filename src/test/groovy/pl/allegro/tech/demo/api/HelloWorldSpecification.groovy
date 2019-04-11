package pl.allegro.tech.demo.api

import pl.allegro.tech.demo.DemoApplicationSpecification

class HelloWorldSpecification extends DemoApplicationSpecification {

//    @LocalServerPort
//    @Shared
//    def port

    def "should return greetings"() {
        when:
        def response = client.get(path: "/hello")

        then:
        with(response) {
            data.text == "hello there Heroku"
        }
    }
}

