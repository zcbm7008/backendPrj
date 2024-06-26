package webshop.common.jpa;

import jakarta.persistence.AttributeConverter;
import webshop.common.model.Money;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money,Integer> {



    @Override
    public Integer convertToDatabaseColumn(Money money) {
        return (money == null) ? null : money.getValue();
    }

    @Override
    public Money convertToEntityAttribute(Integer value) {
        return (value == null) ? null : new Money(value);
    }
}
