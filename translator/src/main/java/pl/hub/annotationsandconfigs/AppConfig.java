package pl.hub.annotationsandconfigs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.hub.datasource.DataSource;
import pl.hub.service.FileEncoder;

import java.io.IOException;

@Configuration
@PropertySource("classpath:message.properties")
public class AppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @ProdProfile
    public DataSource getProdSource() {
        return () -> {
            try {
                FileEncoder.encodeFile(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    @DevProfile
    public DataSource getDevSource() {
        return () -> {
            try {
                FileEncoder.justReturn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
