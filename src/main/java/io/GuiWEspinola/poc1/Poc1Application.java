package io.GuiWEspinola.poc1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class Poc1Application {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        SpringApplication.run(Poc1Application.class, args);
    }

}
