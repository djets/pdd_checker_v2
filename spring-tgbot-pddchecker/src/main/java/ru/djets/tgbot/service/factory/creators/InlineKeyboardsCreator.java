package ru.djets.tgbot.service.factory.creators;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.djets.tgbot.enums.CallbackPrefix;

public interface InlineKeyboardsCreator {
    InlineKeyboardMarkup createInlineKeyboard(
            CallbackPrefix callbackPrefix,
            int numberOfButtons,
            int maxSizeRow
    );

    InlineKeyboardMarkup createInlineKeyboard(
            InlineKeyboardMarkup messageKeyboard,
            int numberSelectedAnswer,
            int correctAnswer
    );
}
