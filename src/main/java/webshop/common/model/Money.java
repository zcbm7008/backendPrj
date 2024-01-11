package webshop.common.model;

import lombok.Setter;

@Setter
public class Money {
    private int value;

    public Money(int value) {
        if (this.value < 0) { throw new IllegalArgumentException("Money value cannot be negative");}
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
