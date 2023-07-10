package by.x1ss.ModsenPractice.config;

import com.fasterxml.jackson.core.JsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public JsonFactory createJsonFactory(){
        return new JsonFactory();
    }
}
