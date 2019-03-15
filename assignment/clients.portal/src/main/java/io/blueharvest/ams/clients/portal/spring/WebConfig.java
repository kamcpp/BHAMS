package io.blueharvest.ams.clients.portal.spring;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean
    @Qualifier("api-key")
    public String getApiKey() {
        return "XYZ";
    }

    @Bean
    @Qualifier("account-endpoint")
    public String getAccountEndpoint() {
        return "http://172.18.0.11:8080";
    }

    @Bean
    @Qualifier("transaction-endpoint")
    public String getTransactionEndpoint() {
        return "http://172.18.0.12:8080";
    }

    @Bean
    @Qualifier("customer-persistence-endpoint")
    public String getCustomerPersistenceEndpoint() {
        return "http://172.18.0.10:8080/customer";
    }

}
