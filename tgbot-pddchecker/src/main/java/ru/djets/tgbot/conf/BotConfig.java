package ru.djets.tgbot.conf;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "telegram")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BotConfig {
    @Value("${telegram.bot-username}")
    String botUsername;
    //    String botToken;
    @Value("${telegram.bot-token-path}")
    String botTokenPath;

    @Value("${telegram.api-url}")
    String apiUrl;

    String botPath = "https://test.url";
}
