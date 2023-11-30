package ru.djets.tgbot.dto.mapper;

import org.springframework.stereotype.Component;
import ru.djets.tgbot.dao.entity.Answer;
import ru.djets.tgbot.dto.AnswerDto;

import java.util.UUID;

@Component
public class AnswerDtoMapper implements DtoMapper<Answer, AnswerDto> {
    @Override
    public AnswerDto toDo(Answer answer) {
        return new AnswerDto(
                answer.getId().toString(),
                answer.getAnswerText(),
                answer.getVersion());
    }

    @Override
    public Answer fromDo(AnswerDto answerDto) {
        Answer answer = new Answer();
        answer.setId(answerDto.getId() != null ? UUID.fromString(answerDto.getId()) : null);
        answer.setAnswerText(answerDto.getAnswerText());
        answer.setVersion(answerDto.getVersion());
        return answer;
    }
}