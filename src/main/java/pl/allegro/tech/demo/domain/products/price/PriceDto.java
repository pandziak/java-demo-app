package pl.allegro.tech.demo.domain.products.price;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.nonNull;

public class PriceDto {

    private final BigDecimal amount;

    private final Currency currency;

    @JsonCreator
    public PriceDto(@JsonProperty("amount") BigDecimal amount,
                    @JsonProperty("currency") Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public PriceDto(Price price) {
        this.amount = price.getAmount();
        this.currency = price.getCurrency();
    }

    @JsonIgnore
    public boolean isValid() {
        return nonNull(amount) && nonNull(currency) && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "PriceDto{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PriceDto priceDto = (PriceDto) o;
        return Objects.equals(amount, priceDto.amount) &&
                currency == priceDto.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
