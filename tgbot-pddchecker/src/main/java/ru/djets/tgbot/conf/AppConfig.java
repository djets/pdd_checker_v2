package ru.djets.tgbot.conf;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.djets.tgbot.dao.entity.BotSettings;
import ru.djets.tgbot.dao.repositories.QuestionRepository;
import ru.djets.tgbot.dao.services.BotSettingsService;
import ru.djets.tgbot.services.WebhookBotService;

@Getter
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AppConfig {

    final BotSettingsService botSettingsService;

    final QuestionRepository questionRepository;

    @Value("${telegram.bot-name}")
    String botName;

    @Bean
    public SetWebhook getSetWebhook() {
        BotSettings botSettings = botSettingsService.findByBotName(botName);
        return SetWebhook.builder()
                .url(botSettings.getBotPath())
                .build();
    }

    @Bean
    public WebhookBotService getWebhookBotService(SetWebhook setWebhook){
        return new WebhookBotService(setWebhook);
    }

}
