package webshop.user.query.seller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Immutable
public class SellerDTO {
    private Long sellerId;
    private String sellerName;
}
