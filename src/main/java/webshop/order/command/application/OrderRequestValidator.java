package webshop.order.command.application;

import webshop.common.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class OrderRequestValidator {

    public List<ValidationError> validate(OrderRequest orderRequest){
        List<ValidationError> errors = new ArrayList<>();
        if (orderRequest == null){
            errors.add(ValidationError.of("required"));
        } else{
            if(orderRequest.getOrdererMemberId() == null)
                errors.add(ValidationError.of("ordererMemberId","required"));
            if(orderRequest.getOrderProducts() == null || orderRequest.getOrderProducts().isEmpty())
                errors.add(ValidationError.of("orderProducts","required"));
        }
        return errors;
    }
}
