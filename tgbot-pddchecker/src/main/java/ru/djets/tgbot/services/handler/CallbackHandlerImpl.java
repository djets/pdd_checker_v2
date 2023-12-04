package ru.djets.tgbot.services.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.djets.tgbot.dao.services.QuestionService;
import ru.djets.tgbot.dto.QuestionDto;
import ru.djets.tgbot.enums.TypeMessage;
import ru.djets.tgbot.enums.TypeSendPhoto;
import ru.djets.tgbot.services.BotStateService;
import ru.djets.tgbot.services.LongPollingBotService;
import ru.djets.tgbot.services.factory.BotObjectFactory;

import java.util.List;

import static ru.djets.tgbot.enums.CallbackPrefix.*;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class CallbackHandlerImpl implements CallbackHandler {

    BotStateService botStateService;

    QuestionService questionService;

    BotObjectFactory<SendMessage, TypeMessage, String> botMessageFactory;

    BotObjectFactory<SendPhoto, TypeSendPhoto, String> botSendPhotoFactory;

    EditMediaHandler editMediaHandler;

    LongPollingBotService longPollingBotService;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) throws TelegramApiException {
        String data = callbackQuery.getData();
        String chatId = callbackQuery.getMessage().getChatId().toString();
        Integer ticketNumber = botStateService.getTicketSelectedMap().get(chatId);

        if (data.startsWith(TICKET_.name())) {
            int numberSelectedTicket = Integer
                    .parseInt(data.replace(TICKET_.toString(), ""));

            botStateService.getTicketSelectedMap()
                    .merge(chatId, numberSelectedTicket, (k, v) -> numberSelectedTicket);

            return botMessageFactory.create(chatId, TypeMessage.TICKET_QUESTIONS);
        }

        if (data.startsWith(QUESTION_.name())) {
            int selectedNumberQuestionDto = Integer
                    .parseInt(data.replace(QUESTION_.toString(), ""));

            if (ticketNumber != null) {
                QuestionDto selectQuestion = questionService
                        .findQuestionByTicketNumberAndNumberQuestion(
                                ticketNumber,
                                selectedNumberQuestionDto);
                saveState(chatId, selectQuestion);

                if (selectQuestion.getPathImage() != null && !selectQuestion.getPathImage().isEmpty()) {
                    longPollingBotService.execute(
                            botSendPhotoFactory.create(chatId, TypeSendPhoto.QUESTION));
                } else {
                    return botMessageFactory.create(chatId, TypeMessage.QUESTION);
                }
            }
        }

        if (data.startsWith(ANSWER_.name())) {
            if (botStateService.getQuestionSelectedMap().containsKey(chatId)) {
                if (callbackQuery.getMessage().hasPhoto()) {
                    longPollingBotService
                            .execute(editMediaHandler.editSendPhoto(callbackQuery));
                } else {
                    return editMediaHandler.editSendMessage(callbackQuery);
                }
            } else {
                return botMessageFactory.create(chatId, TypeMessage.WRONG_SELECTED_QUESTION);
            }
        }

        if (data.startsWith(NEXT_.name())) {
            if (ticketNumber != null) {
                List<QuestionDto> questionDtoList = questionService
                        .getAllByTicketNumber(ticketNumber);

                int numberNextQuestion = botStateService
                        .getQuestionSelectedMap().get(chatId).getNumberQuestion() + 1;

                if (numberNextQuestion < questionDtoList.size() + 1) {
                    QuestionDto nextQuestionDto = questionDtoList.get(numberNextQuestion - 1);
                    saveState(chatId, nextQuestionDto);

                    if (nextQuestionDto.getPathImage() != null && !nextQuestionDto.getPathImage().isEmpty()) {
                        longPollingBotService
                                .execute(botSendPhotoFactory.create(chatId, TypeSendPhoto.QUESTION));
                    } else {
                        return botMessageFactory.create(chatId, TypeMessage.QUESTION);
                    }

                } else {
                    return botMessageFactory.create(chatId, TypeMessage.OUT_OF_QUESTION);
                }
            } else {
            return botMessageFactory.create(chatId, TypeMessage.WRONG_SELECTED_TICKET);
            }
        }
        return null;
    }

    private void saveState(String chatId, QuestionDto nextQuestionDto) {
        botStateService.getQuestionSelectedMap()
                .merge(chatId, nextQuestionDto, (k, v) -> nextQuestionDto);
    }
}
