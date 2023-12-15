package ru.djets.tgbot.dto.mapper;

import org.springframework.stereotype.Component;
import ru.djets.tgbot.dao.entity.TgUser;
import ru.djets.tgbot.dto.TgUserDto;

@Component
public class TgUserDtoMapper implements DtoMapper<TgUser, TgUserDto> {

    @Override
    public TgUserDto toDo(TgUser tgUser) {
        return new TgUserDto()
                .setTgId(tgUser.getTgId());
    }

    @Override
    public TgUser fromDo(TgUserDto tgUserDto) {
        return new TgUser()
                .setTgId(tgUserDto.getTgId());
    }
}
