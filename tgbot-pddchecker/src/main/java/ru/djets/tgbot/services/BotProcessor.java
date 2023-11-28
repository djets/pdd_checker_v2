package ru.djets.tgbot.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.djets.tgbot.dto.TgUserDto;
import ru.djets.tgbot.services.handler.CallbackHandler;
import ru.djets.tgbot.services.handler.MessageHandler;
import ru.djets.tgbot.services.model.TgUserService;


@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BotProcessor {

    MessageHandler messageHandler;

    CallbackHandler callbackHandler;

    TgUserService tgUserService;

    BotApiMethod<?> exUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            TgUserDto tgUserDto = new TgUserDto()
                    .setTgId(update.getCallbackQuery().getMessage().getContact().getUserId())
                    .setName(update.getCallbackQuery().getMessage().getContact().getFirstName())
                    .setName(update.getCallbackQuery().getMessage().getContact().getLastName());
            tgUserService.save(tgUserDto);
            try {
                return callbackHandler.handle(update.getCallbackQuery(), tgUserDto);
            } catch (TelegramApiException e) {
                log.info(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        TgUserDto tgUserDto = new TgUserDto()
                .setTgId(update.getMessage().getContact().getUserId())
                .setName(update.getMessage().getContact().getFirstName())
                .setName(update.getMessage().getContact().getLastName());
        tgUserService.save(tgUserDto);
        return messageHandler.handle(update.getMessage(), tgUserDto);
    }
}
