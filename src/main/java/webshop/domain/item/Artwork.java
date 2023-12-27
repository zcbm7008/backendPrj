package webshop.domain.item;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.core.util.Duration;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("Ar")
public class Artwork extends Item{
	private List<String> artist = new ArrayList<String>();
	private String resolution;

}
