package ru.djets.webclient.dao.services;

import ru.djets.webclient.dto.AnswerDto;

import java.util.List;

public interface AnswerService {
    AnswerDto findByAnswerText(String answerText);

    List<AnswerDto> findAll();

    void deleteAnswer(AnswerDto answerDto);

    void save(AnswerDto answerDto);
}
