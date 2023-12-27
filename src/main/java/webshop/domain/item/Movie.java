package webshop.domain.item;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("Mo")
public class Movie extends Item{
	private String director;
	private List<String> actors = new ArrayList<String>();

}
