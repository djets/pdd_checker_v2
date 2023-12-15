package ru.djets.webclient.dao.services;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.djets.webclient.dao.entity.Answer;
import ru.djets.webclient.dao.entity.Question;
import ru.djets.webclient.dao.repositories.AnswerRepository;
import ru.djets.webclient.dao.repositories.QuestionJpaRepository;
import ru.djets.webclient.dto.QuestionDto;
import ru.djets.webclient.dto.mapper.DtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {

    QuestionJpaRepository questionRepository;

    AnswerRepository answerRepository;

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
    public int getCountQuestionsByTextQuestionIsNotNull() {
        return questionRepository.countQuestionsByTextQuestionIsNotNull();
    }

    @Override
    public List<QuestionDto> findAll() {
        return questionRepository.findAll()
                .stream()
                .map(dtoMapper::toDo)
                .toList();
    }

    @Transactional
    @Override
    public String save(QuestionDto questionDto) {
        Question question = dtoMapper.fromDo(questionDto);
        if (question.getId() != null) {
            Optional<Question> optionalQuestion = questionRepository.findById(question.getId());
            Question questionFromBase = optionalQuestion.orElse(question);
            if (optionalQuestion.isEmpty()) {
                return questionRepository.save(question).getId().toString();
            }
            question.setCreationDate(questionFromBase.getCreationDate());
            question.getAnswer().forEach(answer -> {
                if (answer.getId() != null) {
                    Optional<Answer> optionalAnswer = answerRepository.findById(answer.getId());
                    optionalAnswer.ifPresent(
                            value -> answer.setCreationDate(value.getCreationDate()));
                }
            });
        }
        return questionRepository.save(question).getId().toString();
    }

    @Override
    public List<QuestionDto> searchQuestionByTextQuestionContaining(String searchText) {
        return questionRepository.searchQuestionByTextQuestionContaining(searchText)
                .stream()
                .map(dtoMapper::toDo)
                .toList();
    }

    @Transactional
    @Override
    public void deleteQuestion(QuestionDto questionDto) {
        questionRepository.deleteQuestionById(UUID.fromString(questionDto.getId()));
    }
}
