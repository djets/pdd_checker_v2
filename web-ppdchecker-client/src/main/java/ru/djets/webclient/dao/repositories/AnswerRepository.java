package ru.djets.webclient.dao.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.djets.webclient.dao.entity.Answer;

import java.util.Optional;
import java.util.UUID;

public interface AnswerRepository extends
        ListCrudRepository<Answer, UUID>,
        PagingAndSortingRepository<Answer, UUID> {
    Optional<Answer> findByAnswerTextEqualsIgnoreCase(String string);
}
