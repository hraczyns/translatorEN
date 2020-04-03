package pl.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.hub.controller.LinguController;
import pl.hub.service.FileService;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class TranslatorApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(TranslatorApplication.class, args);
        LinguController controller = ctx.getBean(LinguController.class);
        controller.mainLoop();

    }

    @Bean
    public Scanner scanner(){
        return new Scanner(System.in);
    }

}
