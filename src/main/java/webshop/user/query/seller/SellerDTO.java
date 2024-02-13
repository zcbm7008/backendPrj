package webshop.user.query.seller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Immutable
public class SellerDTO {
    @Id
    private String number;

    private Long sellerId;

    private String sellerName;

}
