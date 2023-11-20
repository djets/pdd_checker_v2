package ru.djets.tgbot.conf;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "telegram")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BotConfig {
    String botUsername;
    //    String botToken;
    String botTokenPath;
    String apiUrl;
    String botPath;
}
