package ru.djets.tgbot.service.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.djets.tgbot.dto.TgUserDto;
import ru.djets.tgbot.service.model.TgUserService;
import ru.djets.tgbot.service.processors.AnswerPostProcessor;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    AnswerPostProcessor answerPostProcessor;

    TgUserService tgUserService;

    @Override
    public BotApiMethod<?> handle(Message message, TgUserDto tgUserDto) {
        String chatId = message.getChatId().toString();
        String messageText = message.getText();

        if(!tgUserService.existTgUserByTgId(tgUserDto.getTgId())) {
            tgUserService.save(tgUserDto);
        };
        // Language must be a set version 17 (Preview) pattern matching for switch
        switch (messageText) {
            case null -> throw new RuntimeException("empty messages text");
            case "/start" -> {
                return null;
            }
            case "вернутся к вопросам" -> {
                if (answerPostProcessor.getTicketSelectedMap().containsKey(chatId)) {
                    return null;
                }
                return null;
            }
            case "выбор билета" -> {
                return null;
            }
            default -> throw new IllegalStateException("Unexpected value: " + messageText);
        }
    }
}
