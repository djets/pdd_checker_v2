package ru.djets.webclient.services;

import com.github.alexdlaird.exception.NgrokException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.djets.webclient.conf.BotConfig;

@Service
@EnableConfigurationProperties(BotConfig.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class TelegramBotRegistrationServiceImpl implements TelegramBotRegistrationService {

//    BotConfig botConfig;

    WebClient webClient;

    NgrokClientService ngrokClientService;

    @Override
    public String registered(String botToken) {
        String uri = null;
        try {
        uri = ("/bot" + botToken +
                "/setWebhook?url=" + ngrokClientService.getBotLocalPath())
                .replace("%0A", "");
        } catch (NgrokException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        log.info("//// START BOT URL REGISTERED");
        String requestSetUrl = webClient
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("//// SET URL: " + requestSetUrl);
        return requestSetUrl;
    }
}
