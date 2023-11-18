package ru.djets.tgbot.service.factory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.djets.tgbot.service.keyboard.KeyboardCreator;

@Service
public class StartMessageCreator implements SendMessageCreator {
    @Override
    public SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Добро пожаловать в бот проверки знаний ПДД! \n" +
                        "Данное приложение создано и запускается ИСКЛЮЧИТЕЛЬНО \nв ознакомительных целях.\n" +
                        "Данные взяты из открытых источников.\n" +
                        "Для начала работы приложения отправьте команду /start")
                .replyMarkup(KeyboardCreator.getMainReplyKeyboard())
                .build();
    }
}
