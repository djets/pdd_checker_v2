package ru.djets.tgbot.service.handler;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface AnswerEditMessageHandler {
    //Create reaction on Answer in SendMessage
    EditMessageText editSendMessage (CallbackQuery callbackQuery);
    //Create reaction on Answer in SendPhoto
    EditMessageMedia editSendPhoto (CallbackQuery callbackQuery);
}