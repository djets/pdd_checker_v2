package ru.djets.tgbot.services;

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
public class BotStateServiceImpl implements BotStateService {

    Map<String, Integer> ticketSelectedMap = new HashMap<>();

    Map<String, QuestionDto> questionSelectedMap = new HashMap<>();

    @Override
    public boolean isCorrectAnswer(int numberSelectedAnswer, QuestionDto questionDto) {
        return questionDto.getNumberCorrectAnswer() == numberSelectedAnswer;
    }

    @Override
    public Map<String, Integer> getTicketSelectedMap() {
        return this.ticketSelectedMap;
    }

    public Map<String, QuestionDto> getQuestionSelectedMap() {
        return this.questionSelectedMap;
    }
}
