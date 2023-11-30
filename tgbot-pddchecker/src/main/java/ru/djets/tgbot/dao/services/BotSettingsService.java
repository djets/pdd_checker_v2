package ru.djets.tgbot.dao.services;

import ru.djets.tgbot.dao.entity.BotSettings;

import java.util.UUID;

public interface BotSettingsService {
    BotSettings findById (UUID id);

    BotSettings findByBotName (String botName);
}
