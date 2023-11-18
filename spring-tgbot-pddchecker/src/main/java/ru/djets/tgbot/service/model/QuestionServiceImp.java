package ru.djets.tgbot.service.model;

import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.djets.tgbot.dto.QuestionDto;
import ru.djets.tgbot.dto.mapper.DtoMapper;
import ru.djets.tgbot.model.Question;
import ru.djets.tgbot.repositories.QuestionJpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {

    QuestionJpaRepository repository;

    DtoMapper<Question, QuestionDto> dtoMapper;

    @Override
    public QuestionDto findById(UUID uuid) {
        return dtoMapper.toDo(repository.findById(uuid)
                .orElseThrow(NotFoundException::new));
    }

    @Override
    public List<QuestionDto> getAllByTicketNumber(int ticketNumber) {
        return repository.findAllByTicketNumber(ticketNumber)
                .stream()
                .map(dtoMapper::toDo)
                .collect(Collectors.toList());
    }

    @Override
    public int getCountAllByTicketNumber(int ticketNumber) {
        return repository.countAllByTicketNumber(ticketNumber);
    }

    @Override
    public int getCountDistinctByTicketNumberExists() {
        return repository.countDistinctByTicketNumberExists();
    }
}
