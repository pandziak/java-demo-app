package pl.allegro.tech.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.connector.Response;
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
import pl.allegro.tech.demo.domain.products.description.DescriptionDto;
import pl.allegro.tech.demo.domain.products.image.ImageDto;
import pl.allegro.tech.demo.domain.products.price.Currency;
import pl.allegro.tech.demo.domain.products.price.PriceDto;
import pl.allegro.tech.demo.domain.products.tag.TagDto;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductEndpointTests extends DemoApplicationTests {

    @Autowired
    ProductFacade productFacade;

    @Test
    public void shouldCreateProduct() {
        // given
        final String url = "http://localhost:" + port + "/api/products/";
        final ProductRequestDto productDto = mockProductRequestDto("produkt");
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
        ProductRequestDto requestDto = mockProductRequestDto("produkt");
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
        ProductRequestDto requestDto1 = mockProductRequestDto("produkt_A");
        ProductRequestDto requestDto2 = mockProductRequestDto("produkt_B");

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
        ProductRequestDto requestDto = mockProductRequestDto("produkt");
        ProductResponseDto existingProduct = productFacade.create(requestDto);

        ProductUpdateDto toUpdate = new ProductUpdateDto(existingProduct.getId(),
                "produkt po update",
                new PriceDto(BigDecimal.valueOf(15L), Currency.EUR),
                requestDto.getImage(),
                new DescriptionDto("nowy opis"),
                Arrays.asList(new TagDto("tag")));

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
        ProductRequestDto requestDto = mockProductRequestDto("produkt");
        ProductResponseDto existingProduct = productFacade.create(requestDto);

        final String deleteUrl = "http://localhost:" + port + "/api/products/" + existingProduct.getId();
        final String getUrl = "http://localhost:" + port + "/api/products/" + existingProduct.getId();

        // when
        ResponseEntity<String> responseDeleteEntity = httpClient.exchange(
                deleteUrl, HttpMethod.DELETE, null, String.class);
        ResponseEntity<String> responseGetEntity = httpClient.getForEntity(getUrl, String.class);

        // then
        assertThat(responseDeleteEntity.getStatusCodeValue()).isEqualTo(204);
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

    private ProductRequestDto mockProductRequestDto(String name) {
        return new ProductRequestDto(
                name,
                new PriceDto(BigDecimal.TEN, Currency.EUR),
                new ImageDto(name + "-obrazek.jpg"),
                new DescriptionDto("Opis bardzo długi."),
                Arrays.asList(new TagDto("tag1"), new TagDto("tag2")));
    }
}
