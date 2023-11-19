package ru.djets.tgbot.service.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.djets.tgbot.dto.QuestionDto;
import ru.djets.tgbot.dto.TgUserDto;
import ru.djets.tgbot.enums.TypeMessage;
import ru.djets.tgbot.enums.TypeSendPhoto;
import ru.djets.tgbot.service.BotStateService;
import ru.djets.tgbot.service.factory.BotObjectFactory;
import ru.djets.tgbot.service.model.QuestionService;

import static ru.djets.tgbot.enums.CallbackPrefix.*;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandlerImpl implements CallbackQueryHandler {

    BotStateService botStateService;

    QuestionService questionService;

    BotObjectFactory<SendMessage, TypeMessage, String> botMessageFactory;

    BotObjectFactory<SendPhoto, TypeSendPhoto, String> botSendPhotoFactory;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery, TgUserDto tgUserDto) {
        String data = callbackQuery.getData();
        String chatId = callbackQuery.getMessage().getChatId().toString();

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
            QuestionDto selectedQuestionDto = questionService
                    .getAllByTicketNumber(botStateService.getTicketSelectedMap().get(chatId))
                    .get(selectedNumberQuestionDto - 1);

            botStateService.getQuestionSelectedMap()
                    .merge(chatId, selectedQuestionDto, (k, v) -> selectedQuestionDto);

            if (selectedQuestionDto.getPathImage().isEmpty()) {
                return botMessageFactory.create(chatId, TypeMessage.QUESTION);
            } else {
                botSendPhotoFactory.create(chatId, TypeSendPhoto.QUESTION);
            }
        }
        if (data.startsWith(ANSWER_.name())) {

        }
        if (data.startsWith(DESCRIPTION_.name())) {

        }
        if (data.startsWith(NEXT_.name())) {

        }

        return null;
    }
}
