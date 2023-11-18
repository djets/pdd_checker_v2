package ru.djets.tgbot.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.djets.tgbot.dto.QuestionDto;
import ru.djets.tgbot.dto.mapper.DtoMapper;
import ru.djets.tgbot.dto.mapper.QuestionDtoMapper;
import ru.djets.tgbot.model.Answer;
import ru.djets.tgbot.model.Question;
import ru.djets.tgbot.repositories.QuestionJpaRepository;
import ru.djets.tgbot.service.model.QuestionService;
import ru.djets.tgbot.service.model.QuestionServiceImp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
class QuestionServiceImpTest {

    @Mock
    static
    QuestionJpaRepository repository;

    static QuestionService questionService;

    static DtoMapper<Question, QuestionDto> dtoMapper;

    Question question;

    UUID idQuestion;

    @BeforeAll
    static void setUpAll() {
        dtoMapper = new QuestionDtoMapper();
        questionService = new QuestionServiceImp(repository, dtoMapper);
    }

    @BeforeEach
    void setUp() {
        idQuestion = UUID.randomUUID();
        question = new Question()
                .setId(idQuestion)
                .setTextQuestion("Text")
                .setDescription("Description")
                .setNumberCorrectAnswer(1)
                .setTicketNumber(1)
                .setPathImage("new Path()")
                .setAnswer(List.of(new Answer()
                        .setId(UUID.randomUUID())
                        .setAnswerText("Text Answer")));
        when(repository.findById(any())).thenReturn(Optional.of(question));

    }

    @Test
    void findById() {
        QuestionDto questionDto = questionService.findById(idQuestion);
        assertThat(dtoMapper.toDo(question)).isEqualTo(questionDto);
    }

    @Test
    void getAllByTicketNumber() {
    }

    @Test
    void getCountAllByTicketNumber() {
    }
}