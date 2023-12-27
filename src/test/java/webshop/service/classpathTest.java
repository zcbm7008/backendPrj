package webshop.service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class classpathTest {

    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("appConfig.xml");
        if (resource == null) {
            System.out.println("appConfig.xml not found in classpath");
        } else {
            System.out.println("appConfig.xml located at: " + resource.toString());
        }
    }

}
