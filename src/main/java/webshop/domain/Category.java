package webshop.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY")
public class Category {
	@Id @GeneratedValue
	@Column(name ="CATEGORY_ID")
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "category")
	private List<CategoryItem> categoryItems = new ArrayList<CategoryItem>();
	
}
