package webshop.common;

import java.util.List;

public class ValidationErrorException extends RuntimeException{

    private List<ValidationError> errors;

    public ValidationErrorException(List <ValidationError> erros) { this.errors = errors;}

    public List<ValidationError> getErrors() {return errors;}
}
