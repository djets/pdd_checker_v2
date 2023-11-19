package ru.djets.tgbot.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LongPollingBotService extends TelegramLongPollingBot {

    //TODO подтягивать конфиг
    String botUsername, botToken;

    @Override
    public void onUpdateReceived(Update update) {}
}
