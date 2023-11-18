package ru.djets.tgbot.service.model;

import ru.djets.tgbot.dto.TgUserDto;

import java.util.UUID;

public interface TgUserService {

    boolean existById(UUID id);

    boolean existTgUserByTgId(long tgId);

    void save (TgUserDto tgUserDto);
}
