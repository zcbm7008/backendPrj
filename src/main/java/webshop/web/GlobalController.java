package webshop.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    @ModelAttribute("currentURI")
    public String currentURI(HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        return request.getRequestURI();

    }
}
