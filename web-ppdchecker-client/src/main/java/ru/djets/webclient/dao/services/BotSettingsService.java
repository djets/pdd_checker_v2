package ru.djets.webclient.dao.services;

import ru.djets.webclient.dao.entity.BotSettings;

import java.util.UUID;

public interface BotSettingsService {
    BotSettings findById (UUID id);

    BotSettings findByBotName (String botName);

    BotSettings save (BotSettings botSettings);
}
