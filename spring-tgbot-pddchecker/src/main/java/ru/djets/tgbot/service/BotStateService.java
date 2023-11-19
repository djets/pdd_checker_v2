package ru.djets.tgbot.service;


import ru.djets.tgbot.dto.QuestionDto;

import java.util.Map;

public interface BotStateService {
    boolean isCorrectAnswer(int numberSelectedAnswer, QuestionDto questionDto);

    Map<String, Integer> getTicketSelectedMap();

    Map<String, QuestionDto> getQuestionSelectedMap();
}