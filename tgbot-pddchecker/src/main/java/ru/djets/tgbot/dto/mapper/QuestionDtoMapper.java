package ru.djets.tgbot.dto.mapper;

import org.springframework.stereotype.Component;
import ru.djets.tgbot.dto.QuestionDto;
import ru.djets.tgbot.model.Answer;
import ru.djets.tgbot.model.Question;

import java.util.List;

@Component
public class QuestionDtoMapper implements DtoMapper<Question, QuestionDto> {

    @Override
    public QuestionDto toDo(Question question) {
        return new QuestionDto()
                .setId(question.getId().toString())
                .setQuestionText(question.getTextQuestion())
                .setDescription(question.getDescription())
                .setPathImage(question.getPathImage())
                .setTicketNumber(question.getTicketNumber())
                .setNumberCorrectAnswer(question.getNumberCorrectAnswer())
                .setAnswers(List.copyOf(question.getAnswer().stream().map(Answer::getAnswerText).toList())
        );
    }

    @Override
    public Question fromDo(QuestionDto questionDto) {
        return null;
    }
}
