package ru.djets.webclient.dao.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.djets.webclient.dao.entity.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionJpaRepository extends
        ListCrudRepository<Question, UUID>,
        PagingAndSortingRepository<Question, UUID> {

    List<Question> findAllByTicketNumber(int ticketNumber);

    int countAllByTicketNumber(int ticketNumber);

    int countQuestionsByTextQuestionIsNotNull();

    List<Question> searchQuestionByTextQuestionContaining(String searchText);

    void deleteQuestionById(UUID id);
}
