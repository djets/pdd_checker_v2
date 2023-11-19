package ru.djets.tgbot.service.factory.creators;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class WrongSelectedTicketMessageCreator implements SendMessageCreator {
    @Override
    public SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text("Вы еще не выбрали билет. " +
                        "Нажмите кнопку 'выбора билета' на встроенной клавиатуре")
                .build();
    }
}
