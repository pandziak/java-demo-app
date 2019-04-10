package pl.allegro.tech.demo.domain.products;

import pl.allegro.tech.demo.domain.products.description.Description;
import pl.allegro.tech.demo.domain.products.image.Image;
import pl.allegro.tech.demo.domain.products.price.Price;
import pl.allegro.tech.demo.domain.products.tag.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Product {

    private final String id;

    private final String name;

    private final Price price;

    private final Image image;

    private final Description description;

    private final List<Tag> tags;

    private final LocalDateTime createdAt;

    public Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.image = builder.image;
        this.description = builder.description;
        this.createdAt = builder.createdAt;
        this.tags = builder.tags;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Image getImage() {
        return image;
    }

    public Description getDescription() {
        return description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(image, product.image) &&
                Objects.equals(description, product.description) &&
                Objects.equals(tags, product.tags) &&
                Objects.equals(createdAt, product.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, image, description, tags, createdAt);
    }

    public static class Builder {
        private String id;
        private String name;
        private Price price;
        private Image image;
        private Description description;
        private LocalDateTime createdAt;

        private List<Tag> tags = new ArrayList<>();

        public Builder(String id, String name, Price price, Image image,
                       Description description, LocalDateTime createdAt) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.image = image;
            this.description = description;
            this.createdAt = createdAt;
        }

        public Builder withTags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
