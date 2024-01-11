package webshop.common.jpa;

import jakarta.persistence.AttributeConverter;
import webshop.common.model.Comment;
import webshop.common.model.Email;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email,String>  {

    @Override
    public String convertToDatabaseColumn(Email email) {
        return (email == null) ? null : email.getValue();
    }

    @Override
    public Email convertToEntityAttribute(String value) {
        return (value == null) ? null : new Email(value);
    }

}
