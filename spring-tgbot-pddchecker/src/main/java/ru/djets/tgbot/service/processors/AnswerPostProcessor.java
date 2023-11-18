package ru.djets.tgbot.service.processors;


import ru.djets.tgbot.dto.QuestionDto;

import java.util.Map;

public interface AnswerPostProcessor {
    boolean isCorrectAnswer(int numberSelectedAnswer, QuestionDto questionDto);

    Map<Long, Integer> getTicketSelectedMap();

    Map<Long, QuestionDto> getQuestionsSelectedMap();
}
