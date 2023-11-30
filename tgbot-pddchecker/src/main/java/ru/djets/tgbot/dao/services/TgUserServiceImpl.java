package ru.djets.tgbot.dao.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.djets.tgbot.dao.entity.TgUser;
import ru.djets.tgbot.dao.repositories.TgUserRepository;
import ru.djets.tgbot.dto.TgUserDto;
import ru.djets.tgbot.dto.mapper.DtoMapper;

import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TgUserServiceImpl implements TgUserService {

    TgUserRepository repository;

    DtoMapper<TgUser, TgUserDto> dtoMapper;

    @Override
    public boolean existById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existTgUserByTgId(long tgId) {
        return repository.existsTgUserByTgId(tgId);
    }

    @Override
    public void save(TgUserDto tgUserDto) {
        repository.save(dtoMapper.fromDo(tgUserDto));
    }
}
