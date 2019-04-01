package pl.allegro.tech.demo.domain.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.nonNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequestDto {

    private final String name;

    @JsonCreator
    public ProductRequestDto(@JsonProperty("name") String name) {
        this.name = name;
    }

    public boolean isValid() {
        return nonNull(name) && !name.isEmpty();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
