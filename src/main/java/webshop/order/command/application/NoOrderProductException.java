package webshop.order.command.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoOrderProductException extends RuntimeException{

    private Long productId;
}
