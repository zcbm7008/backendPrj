package webshop.order.command.domain;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import webshop.User.domain.member.Member;

public class OrderSpec {

    public static Specification<Order> memberNameLike(final String memberName) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if(StringUtils.hasText(memberName)) return null;

                Join<Order, Member> m =
                        root.join("member", JoinType.INNER);
                return builder.like(m.<String>get("name"), "%" + memberName + "%");
                }
            };
        }
    }

