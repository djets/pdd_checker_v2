package ru.djets.webclient.dao.services;


import ru.djets.webclient.dto.TgUserDto;

import java.util.UUID;

public interface TgUserService {

    boolean existById(UUID id);

    boolean existTgUserByTgId(long tgId);

    void save (TgUserDto tgUserDto);
}
