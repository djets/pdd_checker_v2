package ru.djets.tgbot.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Component
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebhookBotService extends SpringWebhookBot {

    //TODO подтягивать конфиг
    String botPath;
    String botUsername;
    String botToken;
    
    public WebhookBotService(SetWebhook setWebhook) {
        super(setWebhook);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }
}
