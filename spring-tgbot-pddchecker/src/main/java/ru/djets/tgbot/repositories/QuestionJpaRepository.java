package ru.djets.tgbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.djets.tgbot.model.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionJpaRepository extends JpaRepository<Question, UUID> {
    List<Question> findAllByTicketNumber(int ticketNumber);
    int countAllByTicketNumber(int ticketNumber);

    int countDistinctByTicketNumberExists();
}
