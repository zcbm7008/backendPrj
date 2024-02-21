package webshop.catalog.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import webshop.common.model.Image;

import javax.persistence.Entity;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Immutable
public class ItemDTO {
    private String name;
    private int price;
    private int stockQuantity;
    private String content;
    private List<Image> images;

}
