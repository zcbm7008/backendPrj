package webshop.order.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Embeddable
public class OrderNo implements Serializable {

    @Column(name = "ORDER_NUMBER")
    private String number;

    protected OrderNo(){
    }

    public static OrderNo of(String number) {return new OrderNo(number); }


}
