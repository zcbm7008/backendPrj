package webshop.catalog.command.domain.product;

import org.springframework.data.jpa.domain.Specification;

public class ItemSpecification {

    public static Specification<Item> nameContains(String name){
        return (root, query, criteriaBuilder) -> {
            if(name == null || name.isEmpty()){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}