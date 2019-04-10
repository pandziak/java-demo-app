package pl.allegro.tech.demo.domain.products.description;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static java.util.Objects.nonNull;

public class DescriptionDto {

    private final String text;

    @JsonCreator
    public DescriptionDto(@JsonProperty("text") String text) {
        this.text = text;
    }

    public DescriptionDto(Description description) {
        this.text = description.getText();
    }

    @JsonIgnore
    public boolean isValid() {
        return nonNull(text) && !text.isEmpty();
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DescriptionDto that = (DescriptionDto) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
