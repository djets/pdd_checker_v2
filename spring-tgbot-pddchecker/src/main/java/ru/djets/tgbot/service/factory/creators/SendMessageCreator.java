package ru.djets.tgbot.service.factory.creators;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendMessageCreator {
    SendMessage createSendMessage (String chatId);
}
