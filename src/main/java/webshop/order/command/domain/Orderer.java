package webshop.order.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Id;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Embeddable
public class Orderer {
    @Id
    private Long memberId;

    @Column(name ="ORDERER_NAME")
    private String name;

    protected Orderer() {

    }


}
