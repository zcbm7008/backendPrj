package webshop.common.jpa;

import jakarta.persistence.AttributeConverter;
import webshop.common.model.Image;
import webshop.common.model.Money;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImageConverter implements AttributeConverter<Image,String> {



    @Override
    public String convertToDatabaseColumn(Image image) {
        return (image == null) ? null : image.getPath();
    }

    @Override
    public Image convertToEntityAttribute(String path) {
        return (path == null) ? null : new Image(path);
    }
}
