package ru.djets.tgbot.services.factory;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.djets.tgbot.enums.TypeMessage;
import ru.djets.tgbot.services.factory.creators.SendMessageCreator;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BotMessageFactory implements BotObjectFactory<SendMessage, TypeMessage, String> {
    SendMessageCreator startSendMessageCreator;
    SendMessageCreator ticketsMessageCreator;
    SendMessageCreator ticketQuestionsMessageCreator;
    SendMessageCreator questionMessageCreator;
    SendMessageCreator outOfQuestionsMessageCreator;
    SendMessageCreator wrongSelectedTicketMessageCreator;
    SendMessageCreator wrongSelectedQuestionMessageCreator;

    public BotMessageFactory(
            @Qualifier("startMessageCreator") SendMessageCreator startSendMessageCreator,
            @Qualifier("ticketsMessageCreator") SendMessageCreator ticketsMessageCreator,
            @Qualifier("ticketQuestionsMessageCreator") SendMessageCreator ticketQuestionsMessageCreator,
            @Qualifier("questionMessageCreator") SendMessageCreator questionMessageCreator,
            @Qualifier("outOfQuestionsMessageCreator") SendMessageCreator outOfQuestionsMessageCreator,
            @Qualifier("wrongSelectedTicketMessageCreator") SendMessageCreator wrongSelectedTicketMessageCreator,
            @Qualifier("wrongSelectedQuestionMessageCreator") SendMessageCreator wrongSelectedQuestionMessageCreator
    )
    {
        this.startSendMessageCreator = startSendMessageCreator;
        this.ticketsMessageCreator = ticketsMessageCreator;
        this.ticketQuestionsMessageCreator = ticketQuestionsMessageCreator;
        this.questionMessageCreator = questionMessageCreator;
        this.outOfQuestionsMessageCreator = outOfQuestionsMessageCreator;
        this.wrongSelectedTicketMessageCreator = wrongSelectedTicketMessageCreator;
        this.wrongSelectedQuestionMessageCreator = wrongSelectedQuestionMessageCreator;
    }


    public SendMessage create(String chatId, TypeMessage typeMessage) {
        switch (typeMessage) {
            case null -> throw new RuntimeException(chatId + ": empty messages type");
            case START -> {
                return startSendMessageCreator.createSendMessage(chatId);
            }
            case TICKETS -> {
                return ticketsMessageCreator.createSendMessage(chatId);
            }
            case TICKET_QUESTIONS -> {
                return ticketQuestionsMessageCreator.createSendMessage(chatId);
            }
            case QUESTION -> {
                return questionMessageCreator.createSendMessage(chatId);
            }
            case OUT_OF_QUESTION -> {
                return outOfQuestionsMessageCreator.createSendMessage(chatId);
            }
            case WRONG_SELECTED_TICKET -> {
                return wrongSelectedTicketMessageCreator.createSendMessage(chatId);
            }
            case WRONG_SELECTED_QUESTION -> {
                return wrongSelectedQuestionMessageCreator.createSendMessage(chatId);
            }
        }
        return null;
    }
}
