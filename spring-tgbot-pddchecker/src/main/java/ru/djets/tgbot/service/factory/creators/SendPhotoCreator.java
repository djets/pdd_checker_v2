package ru.djets.tgbot.service.factory.creators;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public interface SendPhotoCreator {
    SendPhoto createSendPhoto (String chatId);
}
