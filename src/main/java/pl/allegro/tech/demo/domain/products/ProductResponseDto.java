package pl.allegro.tech.demo.domain.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.allegro.tech.demo.domain.products.description.DescriptionDto;
import pl.allegro.tech.demo.domain.products.image.ImageDto;
import pl.allegro.tech.demo.domain.products.price.PriceDto;
import pl.allegro.tech.demo.domain.products.tag.TagDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductResponseDto {

    private final String id;

    private final String name;

    private final PriceDto price;

    private final ImageDto image;

    private final DescriptionDto description;

    private final List<TagDto> tags;

    @JsonCreator
    public ProductResponseDto(@JsonProperty("id") String id,
                              @JsonProperty("name") String name,
                              @JsonProperty("price") PriceDto price,
                              @JsonProperty("image") ImageDto image,
                              @JsonProperty("description") DescriptionDto description,
                              @JsonProperty("tags") List<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.tags = tags;
    }

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = new PriceDto(product.getPrice());
        this.image = new ImageDto(product.getImage());
        this.description = new DescriptionDto(product.getDescription());
        this.tags = product.getTags().stream().map(TagDto::new).collect(Collectors.toList());
    }

    public String getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductResponseDto that = (ProductResponseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(image, that.image) &&
                Objects.equals(description, that.description) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, image, description, tags);
    }
}
