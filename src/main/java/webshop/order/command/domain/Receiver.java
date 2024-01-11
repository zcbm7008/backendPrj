package webshop.order.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import webshop.common.model.Email;

@AllArgsConstructor
@Getter
@Embeddable
public class Receiver {

    @Column
    private String name;

    @Column
    private Email email;

    public Receiver() {

    }
}
