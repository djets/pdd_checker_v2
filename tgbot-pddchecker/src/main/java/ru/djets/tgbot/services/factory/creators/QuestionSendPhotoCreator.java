package ru.djets.tgbot.services.factory.creators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.djets.tgbot.dto.QuestionDto;
import ru.djets.tgbot.enums.CallbackPrefix;
import ru.djets.tgbot.services.BotStateService;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class QuestionSendPhotoCreator implements SendPhotoCreator {

    BotStateService botStateService;

    InlineKeyboardsCreator inlineKeyboardsCreator;

    // A question with a keyboard to answer
    @Override
    public SendPhoto createSendPhoto(String chatId) {
        QuestionDto questionDto = botStateService.getQuestionSelectedMap().get(chatId);
        SendPhoto sendPhoto = new SendPhoto();

        ClassPathResource pathResource = new ClassPathResource(
                questionDto.getPathImage(),
                ClassLoader.getSystemClassLoader()
        );
        log.info("path: {}", pathResource);

        try {
            File image = pathResource.getFile();
            sendPhoto.setPhoto(new InputFile(image));
        } catch (IOException e) {
            throw new RuntimeException("File image not found " + e.getMessage());
        }
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption("Билет: " + questionDto.getTicketNumber() + "\n" +
                "Вопрос: " + questionDto.getNumberQuestion() + "\n\n" +
                questionDto.getTextQuestion() + "\n" +
                "_______\n" +
                questionDto.getAnswers().stream()
                        .map(answerDto ->
                                answerDto.getNumberAnswer() + ". " +
                                        answerDto.getAnswerText())
                        .collect(Collectors.joining("\n")));
        sendPhoto.setReplyMarkup(
                inlineKeyboardsCreator.createInlineKeyboard(
                        CallbackPrefix.ANSWER_,
                        questionDto.getAnswers().size(),
                        5)
        );
        return sendPhoto;
    }
}
