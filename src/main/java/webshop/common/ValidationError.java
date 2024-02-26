package webshop.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationError {

    private String name;
    private String code;

    public boolean hasName() {return name != null;}

    public static ValidationError of(String code) {return new ValidationError(null, code);}
    public static ValidationError of(String name, String code) {return new ValidationError(name, code);}


}
