package ru.djets.tgbot.services.factory.creators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StartMessageCreator implements SendMessageCreator {

    ReplyKeyboardMarkupCreator replyKeyboardMarkupCreator;

    @Override
    public SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Добро пожаловать в бот проверки знаний ПДД! \n" +
                        "Данное приложение создано и запускается ИСКЛЮЧИТЕЛЬНО \nв ознакомительных целях.\n" +
                        "Данные взяты из открытых источников.\n" +
                        "Для начала работы приложения отправьте команду /start")
                .replyMarkup(replyKeyboardMarkupCreator.createReplyKeyboardMarkup())
                .build();
    }
}
