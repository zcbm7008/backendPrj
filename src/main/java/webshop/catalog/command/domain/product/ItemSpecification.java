package webshop.catalog.command.domain.product;

import jakarta.persistence.criteria.Join;
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

    public static Specification<Item> hasCategory(Long categoryId){
        return (root, query, criteriaBuilder) -> {
            Join<Item, Long> categoryIdsJoin = root.join("categoryIds");

            return criteriaBuilder.equal(categoryIdsJoin, categoryId);
        };
    }

    public static Specification<Item> sellerId(Long sellerId){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("sellerId")), sellerId);
        };
    }
}
