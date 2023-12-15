package ru.djets.tgbot.services.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {
    BotApiMethod<?> handle(Message message);
}
