package pl.allegro.tech.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.connector.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import pl.allegro.tech.demo.DemoApplicationTests;
import pl.allegro.tech.demo.domain.products.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductEndpointTests extends DemoApplicationTests {

    @Autowired
    ProductFacade productFacade;

    @Test
    public void shouldCreateProduct() {
        // given
        final String url = "http://localhost:" + port + "/api/products/";
        final ProductRequestDto productDto = new ProductRequestDto("produkt");
        String productJson = mapToJson(productDto);

        // when
        ResponseEntity<ProductResponseDto> result =
                httpClient.postForEntity(url, getHttpRequest(productJson), ProductResponseDto.class);

        // then
        assertThat(result.getStatusCodeValue()).isEqualTo(Response.SC_CREATED);
    }

    @Test
    public void shouldGetExistingProduct() {
        // given
        ProductRequestDto requestDto = new ProductRequestDto("produkt");
        ProductResponseDto existingProduct = productFacade.create(requestDto);
        final String url = "http://localhost:" + port + "/api/products/" + existingProduct.getId();

        // when
        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url, ProductResponseDto.class);

        // then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualToComparingFieldByField(existingProduct);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void shouldGetExistingProducts() {
        // given
        ProductRequestDto requestDto1 = new ProductRequestDto("produkt_A");
        ProductRequestDto requestDto2 = new ProductRequestDto("produkt_B");

        ProductResponseDto existingProduct1 = productFacade.create(requestDto1);
        ProductResponseDto existingProduct2 = productFacade.create(requestDto2);
        final String url = "http://localhost:" + port + "/api/products/";

        // when
        ResponseEntity<ProductsResponseDto> result = httpClient.getForEntity(url, ProductsResponseDto.class);

        // then
        ProductsResponseDto productsResponseDto = result.getBody();
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(productsResponseDto.getProducts().size()).isEqualTo(2);
        assertThat(productsResponseDto.getProducts().get(0)).isEqualToComparingFieldByField(existingProduct1);
        assertThat(productsResponseDto.getProducts().get(1)).isEqualToComparingFieldByField(existingProduct2);
    }

    @Test
    public void shouldUpdateProduct() {
        // given
        ProductRequestDto requestDto = new ProductRequestDto("produkt");
        ProductResponseDto existingProduct = productFacade.create(requestDto);

        ProductUpdateDto toUpdate = new ProductUpdateDto(existingProduct.getId(), "produkt2");
        String productJson = mapToJson(toUpdate);
        final String url = "http://localhost:" + port + "/api/products/";

        // when
        ResponseEntity<ProductResponseDto> result =
                httpClient.exchange(url, HttpMethod.PUT, getHttpRequest(productJson), ProductResponseDto.class);

        // then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualToComparingFieldByField(toUpdate);
    }

    @Test
    public void shouldNotGetNonExistingProduct() {
        // given
        final String url = "http://localhost:" + port + "/api/products/" + 0;

        // when
        ResponseEntity<String> result = httpClient.getForEntity(url, String.class);

        // then
        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void shouldDeleteProduct() {
        // given
        ProductRequestDto requestDto = new ProductRequestDto("produkt");
        ProductResponseDto existingProduct = productFacade.create(requestDto);

        final String deleteUrl = "http://localhost:" + port + "/api/products/" + existingProduct.getId();
        final String getUrl = "http://localhost:" + port + "/api/products/" + existingProduct.getId();

        // when
        ResponseEntity<String> responseDeleteEntity = httpClient.exchange(
                deleteUrl, HttpMethod.DELETE, null, String.class);
        ResponseEntity<String> responseGetEntity = httpClient.getForEntity(getUrl, String.class);

        // then
        assertThat(responseDeleteEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseGetEntity.getStatusCodeValue()).isEqualTo(404);
    }

    private String mapToJson(Object dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<String> getHttpRequest(String json) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "application/json");
        return new HttpEntity<>(json, httpHeaders);
    }
}
