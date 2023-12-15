package ru.djets.tgbot.services.factory.creators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.djets.tgbot.dao.services.QuestionService;
import ru.djets.tgbot.enums.CallbackPrefix;
import ru.djets.tgbot.dto.QuestionDto;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TicketsMessageCreator implements SendMessageCreator {

    QuestionService questionService;

    InlineKeyboardsCreator inlineKeyboardsCreator;

    @Override
    public SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Выберите билет. \n")
                .replyMarkup(inlineKeyboardsCreator.createInlineKeyboard(
                        CallbackPrefix.TICKET_,
                        (int) questionService.findAll()
                                .stream()
                                .map(QuestionDto::getTicketNumber)
                                .distinct()
                                .count(),
                        8))
                .build();
    }
}
