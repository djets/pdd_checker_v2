package ru.djets.tgbot.services.factory.creators;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class WrongSelectedQuestionMessageCreator implements SendMessageCreator {
    @Override
    public SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text("Вы еще не выбрали вопрос. " +
                        "Нажмите кнопку 'вернутся к вопросам' на встроенной клавиатуре")
                .build();
    }
}
