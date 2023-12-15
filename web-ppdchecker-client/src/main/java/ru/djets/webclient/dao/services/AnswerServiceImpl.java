package ru.djets.webclient.dao.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.djets.webclient.dao.entity.Answer;
import ru.djets.webclient.dao.repositories.AnswerRepository;
import ru.djets.webclient.dto.AnswerDto;
import ru.djets.webclient.dto.mapper.DtoMapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnswerServiceImpl implements AnswerService {

    AnswerRepository repository;

    DtoMapper<Answer, AnswerDto> dtoMapper;

    @Override
    public AnswerDto findByAnswerText(String answerText) {
        repository.findByAnswerTextEqualsIgnoreCase(answerText);
        return null;
    }

    @Override
    public List<AnswerDto> findAll() {
        return repository.findAll().stream().map(dtoMapper::toDo).toList();
    }

    @Override
    public void deleteAnswer(AnswerDto answerDto) {
        repository.deleteById(UUID.fromString(answerDto.getId()));
    }

    @Override
    public void save(AnswerDto answerDto) {
        repository.save(dtoMapper.fromDo(answerDto));
    }
}
