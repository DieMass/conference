package die.mass.conference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class TestConfig {

    @Bean
    public JsonSerializer<LocalDateTime> jsonSerializer() {
        return (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, jsonSerializer())
                .create();
    }
}
