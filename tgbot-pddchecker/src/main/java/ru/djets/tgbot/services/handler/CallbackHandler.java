package ru.djets.tgbot.services.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.djets.tgbot.dto.TgUserDto;

public interface CallbackHandler {
    BotApiMethod<?> handle(CallbackQuery callbackQuery, TgUserDto tgUserDto) throws TelegramApiException;
}
