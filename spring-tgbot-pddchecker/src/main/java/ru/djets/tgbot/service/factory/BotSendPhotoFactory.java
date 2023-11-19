package ru.djets.tgbot.service.factory;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import ru.djets.tgbot.enums.TypeSendPhoto;
import ru.djets.tgbot.service.factory.creators.SendPhotoCreator;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BotSendPhotoFactory implements BotObjectFactory<SendPhoto, TypeSendPhoto, String> {
    SendPhotoCreator questionSendPhotoCreator;

    public BotSendPhotoFactory(
            @Qualifier("questionSendPhotoCreator") SendPhotoCreator questionSendPhotoCreator) {
        this.questionSendPhotoCreator = questionSendPhotoCreator;
    }

    @Override
    public SendPhoto create(String chatId, TypeSendPhoto typeSendPhoto) {
        switch (typeSendPhoto) {
            case null -> throw new RuntimeException(chatId + ": empty messages type");
            case QUESTION -> {
                return questionSendPhotoCreator.createSendPhoto(chatId);
            }
        }
        return null;
    }
}
