package ru.djets.tgbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.djets.tgbot.model.TgUser;

import java.util.UUID;

public interface TgUserRepository extends JpaRepository<TgUser, UUID> {
    boolean existsTgUserByTgId (long tgId);
}
