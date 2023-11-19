package ru.djets.tgbot.service.factory.creators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.djets.tgbot.enums.CallbackPrefix;
import ru.djets.tgbot.service.model.QuestionService;
import ru.djets.tgbot.service.BotStateService;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TicketQuestionsMessageCreator implements SendMessageCreator {

    QuestionService questionService;

    BotStateService postProcessor;
    @Override
    public SendMessage createSendMessage(String chatId) {
        Integer numberSelectedTicked = postProcessor.getTicketSelectedMap().get(chatId);
        return SendMessage.builder()
                .chatId(chatId)
                .text("Вопросы билета " + numberSelectedTicked + ". \n")
                .replyMarkup(KeyboardCreator
                        .getInlineKeyboardWithSequenceNumbers(
                                CallbackPrefix.QUESTION_,
                                questionService.getCountAllByTicketNumber(numberSelectedTicked),
                                5))
                .build();
    }
}
