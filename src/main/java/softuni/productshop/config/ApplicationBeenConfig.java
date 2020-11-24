package softuni.productshop.config;


import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import softuni.productshop.utils.ValidatorUtil;
import softuni.productshop.utils.ValidatorUtilImpl;
import softuni.productshop.utils.XmlParseImpl;
import softuni.productshop.utils.XmlParser;

import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeenConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        return LocalDateTime.parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                    }
                })
                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public JsonElement serialize(LocalDateTime date, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    }
                })
                .create();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParseImpl();
    }
}

