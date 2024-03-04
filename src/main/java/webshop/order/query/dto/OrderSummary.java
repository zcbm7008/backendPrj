package webshop.order.query.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import java.time.LocalDateTime;

@Getter
@Entity
@Immutable
@Subselect(
        """
        select o.order_number as number,
        o.orderer_id,
        o.orderer_name,
        o.total_amounts,
        o.state,
        o.order_date,
        p.item_id,
        p.name as item_name
        from orders o inner join order_item ol
            on o.order_number = ol.order_number
            cross join item p
        where
        ol.line_idx = 0
        and ol.item_id = p.item_id
                """
)
@Synchronize({"orders","order_item","item"})


public class OrderSummary {
    @Id
    private String number;
    @Column(name = "orderer_id")
    private Long ordererId;
    @Column(name = "orderer_name")
    private String ordererName;
    @Column(name = "total_amounts")
    private int totalAmounts;
    private String state;
    @Column(name="order_date")
    private LocalDateTime orderDate;
    @Column(name = "item_id")
    private String itemId;
    @Column(name = "item_name")
    private String itemName;

    protected OrderSummary(){}


}
