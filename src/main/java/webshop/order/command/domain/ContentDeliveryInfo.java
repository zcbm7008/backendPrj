package webshop.order.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Embeddable
public class ContentDeliveryInfo {

    @Column
    private String message;
    @Embedded
    private Receiver receiver;

    public ContentDeliveryInfo() {

    }
}
