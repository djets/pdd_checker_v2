package ru.djets.tgbot.service.processors;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.djets.tgbot.dto.QuestionDto;

import java.util.HashMap;
import java.util.Map;

@Component
@Setter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnswerPostProcessorImpl implements AnswerPostProcessor {

    Map<Long, Integer> ticketSelectedMap = new HashMap<>();
    Map<Long, QuestionDto> questionsSelectedMap = new HashMap<>();

    @Override
    public boolean isCorrectAnswer(int numberSelectedAnswer, QuestionDto questionDto) {
        if (questionDto.getNumberCorrectAnswer() == numberSelectedAnswer) {
            return true;
        }
        return false;
    }

    @Override
    public Map<Long, Integer> getTicketSelectedMap() {
        return this.ticketSelectedMap;
    }

    @Override
    public Map<Long, QuestionDto> getQuestionsSelectedMap() {
        return this.questionsSelectedMap;
    }
}
