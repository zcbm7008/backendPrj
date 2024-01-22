package webshop.common.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class Email {
    private final String value;

    public Email(String value) {
        if(value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.value = value;
    }

}
