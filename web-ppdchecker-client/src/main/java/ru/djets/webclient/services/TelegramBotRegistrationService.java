package ru.djets.webclient.services;

import ru.djets.webclient.dao.entity.BotSettings;

public interface TelegramBotRegistrationService {
    String registered(BotSettings botSettings);
}
