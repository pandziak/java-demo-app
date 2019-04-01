package pl.allegro.tech.demo.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.allegro.tech.demo.DemoApplicationTests;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldTests extends DemoApplicationTests {

    @Test
    public void shouldReturnGreetings() {
        //given
        final String url = "http://localhost:" + port + "/hello";

        // when
        ResponseEntity<String> responseEntity = httpClient.getForEntity(url, String.class);

        // then
        assertThat(responseEntity.getStatusCodeValue() == 200);
        assertThat(responseEntity.getBody() == "hello there Heroku");
    }
}
