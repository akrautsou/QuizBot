package kurs.krautsou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println("Start");
        ApiContextInitializer.init();
        SpringApplication.run(Application.class);
    }

}
