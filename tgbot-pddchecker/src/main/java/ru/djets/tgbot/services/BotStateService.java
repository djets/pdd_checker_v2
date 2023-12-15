package ru.djets.tgbot.services;


import ru.djets.tgbot.dto.QuestionDto;

import java.util.Map;

public interface BotStateService {
    Map<String, Integer> getTicketSelectedMap();

    Map<String, QuestionDto> getQuestionSelectedMap();

}
