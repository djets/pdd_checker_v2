package ru.djets.webclient.dao.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.djets.webclient.dao.entity.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionJpaRepository extends
        ListCrudRepository<Question, UUID>,
        PagingAndSortingRepository<Question, UUID> {

    int countAllByTicketNumber(int ticketNumber);

    int countQuestionsByTextQuestionIsNotNull();

    void deleteQuestionById(UUID id);

    List<Question> findAllByTicketNumber(int ticketNumber);

    List<Question> searchQuestionByTextQuestionContaining(String searchText);
}
