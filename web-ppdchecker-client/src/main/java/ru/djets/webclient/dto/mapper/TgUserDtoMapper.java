package ru.djets.webclient.dto.mapper;

import org.springframework.stereotype.Component;
import ru.djets.webclient.dto.TgUserDto;
import ru.djets.webclient.dao.entity.TgUser;

@Component
public class TgUserDtoMapper implements DtoMapper<TgUser, TgUserDto> {

    @Override
    public TgUserDto toDo(TgUser tgUserEntity) {
        return new TgUserDto()
                .setTgId(tgUserEntity.getTgId());
    }

    @Override
    public TgUser fromDo(TgUserDto tgUserDto) {
        return new TgUser()
                .setTgId(tgUserDto.getTgId());
    }
}
