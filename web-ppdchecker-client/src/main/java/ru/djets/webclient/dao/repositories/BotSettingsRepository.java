package ru.djets.webclient.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.djets.webclient.dao.entity.BotSettings;

import java.util.UUID;

public interface BotSettingsRepository extends JpaRepository<BotSettings, UUID> {
    BotSettings findByBotName(String botName);
}
