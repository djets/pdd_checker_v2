package ru.djets.webclient.services;

import com.github.alexdlaird.exception.NgrokException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.djets.webclient.dao.entity.BotSettings;
import ru.djets.webclient.dao.services.BotSettingsService;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class TelegramBotRegistrationServiceImpl implements TelegramBotRegistrationService {

    BotSettingsService botSettingsService;

    WebClient webClient;

    NgrokClientService ngrokClientService;

    @Override
    public String registered(BotSettings botSettings) {
        botSettings.setBotPath(ngrokClientService.getBotLocalPath());

        String uri = null;
        try {
        uri = ("/bot" + botSettings.getBotToken() +
                "/setWebhook?url=" + botSettings.getBotPath())
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

        botSettings.setStatus(requestSetUrl);

        botSettingsService.save(botSettings);
        return requestSetUrl;
    }
}
