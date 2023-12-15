package ru.djets.tgbot.services.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.djets.tgbot.enums.TypeMessage;
import ru.djets.tgbot.services.BotStateService;
import ru.djets.tgbot.services.factory.BotMessageFactory;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    BotStateService botStateService;

    BotMessageFactory botMessageFactory;

    @Override
    public BotApiMethod<?> handle(Message message) {
        String chatId = message.getChatId().toString();
        String messageText = message.getText();

        // Language must be a set version 17 (Preview) pattern matching for switch
        switch (messageText) {
            case null -> throw new RuntimeException("empty messages text");
            case "/start" -> {
                return botMessageFactory.create(chatId, TypeMessage.START);
            }
            case "вернутся к вопросам" -> {
                if (botStateService.getTicketSelectedMap().containsKey(chatId)) {
                    return botMessageFactory.create(chatId, TypeMessage.TICKET_QUESTIONS);
                }
                return botMessageFactory.create(chatId, TypeMessage.WRONG_SELECTED_TICKET);
            }
            case "выбор билета" -> {
                return botMessageFactory.create(chatId, TypeMessage.TICKETS);
            }
            default -> throw new IllegalStateException("Unexpected value: " + messageText);
        }
    }
}
