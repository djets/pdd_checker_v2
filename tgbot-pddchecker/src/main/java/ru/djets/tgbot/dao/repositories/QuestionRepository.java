package ru.djets.tgbot.dao.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.djets.tgbot.dao.entity.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository extends
        ListCrudRepository<Question, UUID>,
        PagingAndSortingRepository<Question, UUID> {

    int countAllByTicketNumber(int ticketNumber);

    List<Question> findAllByTicketNumber(int ticketNumber);

    List<Question> searchQuestionByTextQuestionContaining(String searchText);

    Optional<Question> findQuestionByTicketNumberAndNumberQuestion(int ticketNumber, int numberQuestion);
}
