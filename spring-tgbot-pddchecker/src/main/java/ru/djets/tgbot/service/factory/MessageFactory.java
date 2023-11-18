package ru.djets.tgbot.service.factory;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.djets.tgbot.enums.TypeMessage;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageFactory {
    SendMessageCreator startSendMessageCreator;
    SendMessageCreator ticketsMessageCreator;

    public MessageFactory(
            @Qualifier("startMessageCreator") @Autowired SendMessageCreator startSendMessageCreator,
            @Qualifier("ticketsMessageCreator") @Autowired SendMessageCreator ticketsMessageCreator)
    {
        this.startSendMessageCreator = startSendMessageCreator;
        this.ticketsMessageCreator = ticketsMessageCreator;
    }


    SendMessage createSendMessage(String chatId, TypeMessage typeMessage) {
        switch (typeMessage) {
            case null -> throw new RuntimeException("empty messages type");
            case START_ -> {
                return startSendMessageCreator.createSendMessage(chatId);
            }
            case ERROR_ -> {
            }
            case BACK_TO_QUESTIONS_ -> {
            }
            case ALL_TICKETS -> {
                return ticketsMessageCreator.createSendMessage(chatId);
            }
            case SELECT_TICKET_ -> {
            }
        }
        return null;
    }
}
