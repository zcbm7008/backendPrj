package webshop.catalog.query.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "category")
public class CategoryData {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    protected CategoryData() {

    }


}
