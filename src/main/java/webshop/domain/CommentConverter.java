package webshop.domain;

import jakarta.persistence.AttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CommentConverter implements AttributeConverter<Comment,String> {



    @Override
    public String convertToDatabaseColumn(Comment comment) {
        return (comment == null) ? null : comment.getValue() + comment.getCreationDate();
    }

    @Override
    public Comment convertToEntityAttribute(String value) {
        return (value == null) ? null : new Comment(value);
    }
}
