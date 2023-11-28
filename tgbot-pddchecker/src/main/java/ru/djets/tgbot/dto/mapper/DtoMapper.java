package ru.djets.tgbot.dto.mapper;

public interface DtoMapper <T, N>{
    N toDo(T t);

    T fromDo(N n);
}
