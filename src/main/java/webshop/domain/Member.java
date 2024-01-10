package webshop.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {

	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;
	
	private String name;

	public Member(String name) {
		setName(name);
	}
	
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<Seller> sellers = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Review> reviews = new ArrayList<>();

}
