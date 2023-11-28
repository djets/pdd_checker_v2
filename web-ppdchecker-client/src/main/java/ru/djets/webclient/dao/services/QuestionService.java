package ru.djets.webclient.dao.services;

import ru.djets.webclient.dto.QuestionDto;

import java.util.List;
import java.util.UUID;

public interface QuestionService {
    QuestionDto findById(UUID uuid);

    List<QuestionDto> getAllByTicketNumber(int ticketNumber);

    int getCountAllByTicketNumber(int ticketNumber);

    int getCountQuestionsByTextQuestionIsNotNull();

    List<QuestionDto> findAll();

    String save(QuestionDto questionDto);

    List<QuestionDto> searchQuestionByTextQuestionContaining(String searchText);

    void deleteQuestion(QuestionDto questionDto);
}
