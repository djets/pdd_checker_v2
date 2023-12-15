package ru.djets.tgbot.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.djets.tgbot.dao.entity.TgUser;

import java.util.UUID;

public interface TgUserRepository extends JpaRepository<TgUser, UUID> {
    boolean existsTgUserByTgId (long tgId);
}
