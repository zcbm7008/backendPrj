package webshop.common.event;

import lombok.Getter;

@Getter
public class Event {

    private long timestamp;

    public Event() {this.timestamp = System.currentTimeMillis();}


}
