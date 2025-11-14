package su.aleksokol3.employeeservice.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration(proxyBeanMethods = false)
public class JsonConfig {

    @Bean
    JsonNullableModule jsonNullableModule() {
        return new JsonNullableModule();
    }

    @Bean
    Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder(JsonNullableModule jsonNullableModule) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .modulesToInstall(jsonNullableModule)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return builder;
    }
}
