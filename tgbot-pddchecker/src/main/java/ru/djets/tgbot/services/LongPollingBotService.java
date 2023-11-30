package ru.djets.tgbot.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.djets.tgbot.conf.AppConfig;
import ru.djets.tgbot.dao.services.BotSettingsService;

@Service
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LongPollingBotService extends TelegramLongPollingBot {

    final AppConfig appConfig;

    final BotSettingsService botSettingsService;

    String botUsername, botToken;

    public LongPollingBotService (
            AppConfig appConfig,
            BotSettingsService botSettingsService
    ) {
        this.appConfig = appConfig;
        this.botSettingsService = botSettingsService;
        this.botUsername = appConfig.getBotName();
        this.botToken = botSettingsService.findByBotName(botUsername).getBotToken();
    }
    @Override
    public void onUpdateReceived(Update update) {}
}
