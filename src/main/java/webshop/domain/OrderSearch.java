package webshop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;
import static webshop.domain.OrderSpec.memberNameLike;

@Getter
@Setter
public class OrderSearch {

    private String memberName;

    public Specification<Order> toSpecification() {
        return where(memberNameLike(memberName));

    }
}
