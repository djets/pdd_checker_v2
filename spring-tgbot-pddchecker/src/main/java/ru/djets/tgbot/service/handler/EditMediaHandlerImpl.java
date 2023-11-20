package ru.djets.tgbot.service.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.djets.tgbot.dto.QuestionDto;
import ru.djets.tgbot.enums.CallbackPrefix;
import ru.djets.tgbot.service.BotStateService;
import ru.djets.tgbot.service.factory.creators.InlineKeyboardsCreator;

import java.util.Comparator;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EditMediaHandlerImpl implements EditMediaHandler {

    BotStateService service;

    InlineKeyboardsCreator inlineKeyboardsCreator;

    @Override
    public EditMessageText editSendMessage(CallbackQuery callbackQuery) {
        String chatId = callbackQuery.getMessage().getChatId().toString();
        QuestionDto questionDto = service.getQuestionSelectedMap().get(chatId);

        int numberSelectedAnswer = Integer.parseInt(callbackQuery
                .getData()
                .replace(CallbackPrefix.ANSWER_.toString(), ""));

        EditMessageText editMessageText = new EditMessageText();

        InlineKeyboardMarkup messageKeyboard = callbackQuery.getMessage().getReplyMarkup();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setText(callbackQuery.getMessage().getText() +
                "\n\n" + questionDto.getDescription());
        editMessageText.setReplyMarkup(
                inlineKeyboardsCreator.createInlineKeyboard(
                        messageKeyboard,
                        numberSelectedAnswer,
                        questionDto.getNumberCorrectAnswer()));
        return editMessageText;
    }

    @Override
    public EditMessageMedia editSendPhoto(CallbackQuery callbackQuery) {
        String chatId = callbackQuery.getMessage().getChatId().toString();
        QuestionDto questionDto = service.getQuestionSelectedMap().get(chatId);

        int numberSelectedAnswer = Integer.parseInt(callbackQuery
                .getData()
                .replace(CallbackPrefix.ANSWER_.toString(), ""));

        EditMessageMedia editMessageMedia = new EditMessageMedia();
        String caption = callbackQuery.getMessage().getCaption();
        InlineKeyboardMarkup messageKeyboard = callbackQuery.getMessage().getReplyMarkup();
        List<PhotoSize> photos = callbackQuery.getMessage().getPhoto();
        List<InputMediaPhoto> inputMediaPhotos = photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .map(PhotoSize::getFileId)
                .map(InputMediaPhoto::new)
                .toList();
        inputMediaPhotos.stream()
                .findFirst()
                .orElseThrow()
                .setCaption(caption + "\n\n" + questionDto.getDescription());
//                .findFirst().findFirst()
//        String fileId = photos.stream()
//                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
//                .findFirst()
//                .orElse(null).getFileId();
//        InputMediaPhoto inputMediaPhoto = new InputMediaPhoto(fileId);

//        inputMediaPhoto.setCaption(caption + "\n\n" + questionDto.getDescription());

        inputMediaPhotos.forEach(editMessageMedia::setMedia);

        editMessageMedia.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessageMedia.setMessageId(callbackQuery.getMessage().getMessageId());
//        editMessageMedia.setMedia(inputMediaPhoto);
        editMessageMedia.setReplyMarkup(
                inlineKeyboardsCreator.createInlineKeyboard(
                        messageKeyboard,
                        numberSelectedAnswer,
                        questionDto.getNumberCorrectAnswer()));
        return editMessageMedia;
    }
}
