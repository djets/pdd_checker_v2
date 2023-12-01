package ru.djets.tgbot.dto.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.djets.tgbot.dao.entity.Question;
import ru.djets.tgbot.dto.QuestionDto;

import java.util.List;
import java.util.UUID;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class QuestionDtoMapper implements DtoMapper<Question, QuestionDto> {

    AnswerDtoMapper answerDtoMapper;

    @Override
    public QuestionDto toDo(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId().toString());
        questionDto.setTextQuestion(question.getTextQuestion());
        questionDto.setDescription(question.getDescription());
        questionDto.setPathImage(question.getPathImage());
        questionDto.setNumberQuestion(question.getNumberQuestion());
        questionDto.setNumberCorrectAnswer(question.getNumberCorrectAnswer());
        questionDto.setTicketNumber(question.getTicketNumber());
        questionDto.setAnswers(
                List.copyOf(question.getAnswer()
                        .stream()
                        .map(answerDtoMapper::toDo)
                        .toList()));
        questionDto.setVersion(question.getVersion());
        return questionDto;
    }

    @Override
    public Question fromDo(QuestionDto questionDto) {
        Question question = new Question();
        question.setTextQuestion(questionDto.getTextQuestion());
        question.setDescription(questionDto.getDescription());
        question.setPathImage(questionDto.getPathImage());
        question.setNumberQuestion(questionDto.getNumberQuestion());
        question.setNumberCorrectAnswer(questionDto.getNumberCorrectAnswer());
        question.setTicketNumber(questionDto.getTicketNumber());
        question.setAnswer(
                questionDto.getAnswers() != null ? List.copyOf(
                        questionDto.getAnswers()
                                .stream()
                                .map(answerDtoMapper::fromDo)
                                .toList()) : null);
        question.setId(questionDto.getId() != null ? UUID.fromString(questionDto.getId()) : null);
        question.setVersion(questionDto.getVersion());
        return question;
    }
}
