package webshop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
public class Money {
    private int value;

    public Money(int value) {
        setValue(value);
    }

    public int getValue() {
        return value;
    }

    public Money add(Money money){
        return new Money(this.value + money.value);
    }

    public Money multiply(int multiplier){
        return new Money(this.value * multiplier);
    }
}
