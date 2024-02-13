package webshop.springconfig.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addRedirectViewController("/","/home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/loggedOut").setViewName("loggedOut");
        registry.addViewController("/my/main").setViewName("my/myMain");
    }
}
