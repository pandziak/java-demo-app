package pl.allegro.tech.demo.domain.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.nonNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductUpdateDto {

    public final String id;

    public final String name;

    @JsonCreator
    public ProductUpdateDto(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isValid() {
        return nonNull(name) && !name.isEmpty() && nonNull(id) && !id.isEmpty();
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }
}
