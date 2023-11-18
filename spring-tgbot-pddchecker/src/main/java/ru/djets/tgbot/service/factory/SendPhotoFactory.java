package ru.djets.tgbot.service.factory;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendPhotoFactory {
    SendMessage createSendPhoto (String chatId);
}
