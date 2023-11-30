package ru.djets.tgbot.dao.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.djets.tgbot.dao.entity.Question;
import ru.djets.tgbot.dao.repositories.QuestionRepository;
import ru.djets.tgbot.dto.QuestionDto;
import ru.djets.tgbot.dto.mapper.DtoMapper;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {

    QuestionRepository questionRepository;

    DtoMapper<Question, QuestionDto> dtoMapper;

    @Override
    public QuestionDto findById(UUID uuid) {
        return dtoMapper.toDo(questionRepository.findById(uuid)
                .orElseThrow());
    }

    @Override
    public List<QuestionDto> getAllByTicketNumber(int ticketNumber) {
        return questionRepository.findAllByTicketNumber(ticketNumber)
                .stream()
                .map(dtoMapper::toDo)
                .toList();
    }

    @Override
    public int getCountAllByTicketNumber(int ticketNumber) {
        return questionRepository.countAllByTicketNumber(ticketNumber);
    }

    @Override
    public List<QuestionDto> findAll() {
        return questionRepository.findAll()
                .stream()
                .map(dtoMapper::toDo)
                .toList();
    }

    @Override
    public List<QuestionDto> searchQuestionByTextQuestionContaining(String searchText) {
        return questionRepository.searchQuestionByTextQuestionContaining(searchText)
                .stream()
                .map(dtoMapper::toDo)
                .toList();
    }
}
