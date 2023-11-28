package ru.djets.webclient.conf;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ConfigurationProperties(prefix = "telegram")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@Slf4j
public class BotConfig {

    @NotEmpty
    String botUsername;
//    @NotEmpty
//    String botTokenPath;
    @NotEmpty
    String apiUrl;

//    public String getToken() {
//        try {
//            File file = new File(this.botTokenPath);
//            if(file.exists() && file.canRead()) {
//                return Files.readString(file.toPath(), StandardCharsets.US_ASCII).trim();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("The token file was not found");
//        }
//        return null;
//    }
}
