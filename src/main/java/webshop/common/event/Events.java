package webshop.common.event;

import org.springframework.context.ApplicationEventPublisher;

public class Events {

    private static ApplicationEventPublisher publisher;

    static void setPublisher(ApplicationEventPublisher publisher) {Events.publisher = publisher;}

    public static void raise(Object event) {
        if (publisher != null) {
            publisher.publishEvent(event);
            // 로그 추가
            System.out.println("Event published: " + event);
        } else {
            // 로그 추가
            System.out.println("Publisher is null. Event not published.");
        }
    }
}
