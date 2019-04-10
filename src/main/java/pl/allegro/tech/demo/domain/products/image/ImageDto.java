package pl.allegro.tech.demo.domain.products.image;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static java.util.Objects.nonNull;

public class ImageDto {

    private final String url;

    @JsonCreator
    public ImageDto(@JsonProperty("url") String url) {
        this.url = url;
    }

    public ImageDto(Image image) {
        this.url = image.getUrl();
    }

    @JsonIgnore
    public boolean isValid() {
        return nonNull(url) && !url.isEmpty();
    }

    public String getUrl() {
        return url;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ImageDto imageDto = (ImageDto) o;
        return Objects.equals(url, imageDto.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
