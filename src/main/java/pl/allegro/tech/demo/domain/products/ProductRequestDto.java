package pl.allegro.tech.demo.domain.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.allegro.tech.demo.domain.products.description.DescriptionDto;
import pl.allegro.tech.demo.domain.products.image.Image;
import pl.allegro.tech.demo.domain.products.image.ImageDto;
import pl.allegro.tech.demo.domain.products.price.PriceDto;
import pl.allegro.tech.demo.domain.products.tag.TagDto;

import java.util.List;

import static java.util.Objects.nonNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequestDto {

    private final String name;

    private final PriceDto price;

    private final ImageDto image;

    private final DescriptionDto description;

    private final List<TagDto> tags;

    @JsonCreator
    public ProductRequestDto(@JsonProperty("name") String name,
                             @JsonProperty("price") PriceDto price,
                             @JsonProperty("image") ImageDto image,
                             @JsonProperty("description") DescriptionDto description,
                             @JsonProperty("tags") List<TagDto> tags) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.tags = tags;
    }

    @JsonIgnore
    public boolean isValid() {
        return nonNull(name) && !name.isEmpty()
                && price.isValid() && description.isValid() && image.isValid();
    }

    public String getName() {
        return name;
    }

    public PriceDto getPrice() {
        return price;
    }

    public ImageDto getImage() {
        return image;
    }

    public DescriptionDto getDescription() {
        return description;
    }

    public List<TagDto> getTags() {
        return tags;
    }
}
