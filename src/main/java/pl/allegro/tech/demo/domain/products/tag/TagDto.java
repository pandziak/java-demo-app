package pl.allegro.tech.demo.domain.products.tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TagDto {

    private final String name;

    @JsonCreator
    public TagDto(@JsonProperty("name") String name) {
        this.name = name;
    }

    public TagDto(Tag tag) {
        this.name = tag.getName();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TagDto tagDto = (TagDto) o;
        return Objects.equals(name, tagDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
