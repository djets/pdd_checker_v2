package ru.djets.tgbot.services.handler;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface EditMediaHandler {
    //Create reaction on Answer in SendMessage
    EditMessageText editSendMessage (CallbackQuery callbackQuery);
    //Create reaction on Answer in SendPhoto
    EditMessageMedia editSendPhoto (CallbackQuery callbackQuery);
}
