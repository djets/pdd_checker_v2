package ru.djets.tgbot.service.factory.creators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.djets.tgbot.enums.CallbackPrefix;
import ru.djets.tgbot.service.model.QuestionService;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TicketsMessageCreator implements SendMessageCreator {

    QuestionService questionService;

    @Override
    public SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Выберите билет. \n")
                .replyMarkup(KeyboardCreator
                        .getInlineKeyboardWithSequenceNumbers(
                                CallbackPrefix.TICKET_,
                                questionService.getCountDistinctByTicketNumberExists(),
                                8))
                .build();
    }
}
