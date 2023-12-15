package ru.djets.webclient.dao.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.djets.webclient.dao.entity.BotSettings;
import ru.djets.webclient.dao.repositories.BotSettingsRepository;

import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BotSettingsServiceImpl implements BotSettingsService {

    BotSettingsRepository repository;
    @Override
    public BotSettings findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public BotSettings findByBotName(String botName) {
        return repository.findByBotName(botName);
    }

    @Override
    public BotSettings save(BotSettings botSettings) {
        return repository.save(botSettings);
    }
}
