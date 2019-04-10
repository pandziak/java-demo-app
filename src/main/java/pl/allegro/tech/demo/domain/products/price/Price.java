package pl.allegro.tech.demo.domain.products.price;

import java.math.BigDecimal;
import java.util.Objects;

public final class Price {

    private final BigDecimal amount;

    private final Currency currency;

    public Price(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Price(Price price) {
        this.amount = price.getAmount();
        this.currency = price.getCurrency();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Price{" +
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
        Price price = (Price) o;
        return Objects.equals(amount, price.amount) &&
                currency == price.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
