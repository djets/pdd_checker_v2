package ru.djets.tgbot.service.factory.creators;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class OutOfQuestionsMessageCreator implements SendMessageCreator {
    @Override
    public SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("В этом билете закончились вопросы. " +
                        "Перейдите к выбору билета нажав 'выбор билета'")
                .build();
    }
}
