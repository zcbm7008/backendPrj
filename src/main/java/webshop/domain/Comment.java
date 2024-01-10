package webshop.domain;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class Comment {
    private final String value;
    private final LocalDateTime creationDate;

    public Comment(String value) {
        if(value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException("Comment cannot be null or empty");
        }
        this.value = value;
        this.creationDate = LocalDateTime.now();
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
