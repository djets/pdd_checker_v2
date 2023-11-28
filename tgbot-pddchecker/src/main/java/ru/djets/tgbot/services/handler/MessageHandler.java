package ru.djets.tgbot.services.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.djets.tgbot.dto.TgUserDto;

public interface MessageHandler {
    BotApiMethod<?> handle(Message message, TgUserDto tgUserDto);
}
