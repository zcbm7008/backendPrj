package webshop.catalog.command.domain.category;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import webshop.catalog.command.domain.product.Item;

@Getter
@AllArgsConstructor
@Entity
@Table(name = "CATEGORY")
public class Category {
	@Id
	private Long id;
	
	private String name;

	protected Category() {

	}
}
