package ru.djets.tgbot.services.factory.creators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.djets.tgbot.dto.QuestionDto;
import ru.djets.tgbot.enums.CallbackPrefix;
import ru.djets.tgbot.services.BotStateService;

import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class QuestionMessageCreator implements SendMessageCreator {

    BotStateService botStateService;

    InlineKeyboardsCreator inlineKeyboardsCreator;

    // A question with a keyboard to answer
    @Override
    public SendMessage createSendMessage(String chatId) {
        QuestionDto questionDto = botStateService.getQuestionSelectedMap().get(chatId);
        return SendMessage.builder()
                .chatId(chatId)
                .text("Билет: " + questionDto.getTicketNumber() + "\n" +
                        "Вопрос: " + questionDto.getNumberQuestion() + "\n\n" +
                        questionDto.getTextQuestion() + "\n" +
                        "_______\n" +
                        questionDto.getAnswers().stream()
                                .map(answerDto ->
                                        answerDto.getNumberAnswer() + ". " +
                                                answerDto.getAnswerText())
                                .collect(Collectors.joining("\n")))
                .replyMarkup(inlineKeyboardsCreator.createInlineKeyboard(
                        CallbackPrefix.ANSWER_,
                        questionDto.getAnswers().size(),
                        5))
                .build();
    }
}
