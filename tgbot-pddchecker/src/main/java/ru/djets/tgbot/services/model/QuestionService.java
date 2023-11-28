package ru.djets.tgbot.services.model;

import ru.djets.tgbot.dto.QuestionDto;

import java.util.List;
import java.util.UUID;

public interface QuestionService {
    QuestionDto findById(UUID uuid);

    List<QuestionDto> getAllByTicketNumber(int ticketNumber);

    int getCountAllByTicketNumber(int ticketNumber);

    int getCountQuestionsByTextQuestionIsNotNull();
}