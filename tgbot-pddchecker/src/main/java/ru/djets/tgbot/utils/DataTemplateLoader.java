package ru.djets.tgbot.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.djets.tgbot.model.Answer;
import ru.djets.tgbot.model.Question;
import ru.djets.tgbot.repositories.QuestionJpaRepository;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DataTemplateLoader {

    QuestionJpaRepository questionJpaRepository;

    public void createQuestion(int numberQuestions) {
        AtomicInteger ticketNumber = new AtomicInteger(1);
        Stream.iterate(1, n -> n + 1)
                .limit(numberQuestions)
                .map(integer -> {
                    Question question = new Question();
                    question.setTextQuestion("Вопрос: " + integer + "Разрешен ли Вам съезд на дорогу с грунтовым покрытием?");
                    question.setDescription("Description: " + integer);
                    question.setPathImage("images/0102.jpg");
                    question.setTicketNumber(ticketNumber.getAndIncrement());
                    question.setNumberCorrectAnswer(new Random().nextInt((3 - 1) + 1) + 1);
                    question.setAnswer(Stream.iterate(1, n -> n + 1)
                            .limit(3)
                            .map(integer1 -> {
                                Answer answer = new Answer();
                                answer.setAnswerText("Answer-" + integer1);
                                return answer;
                            })
                            .toList());
                    return question;
                })
                .peek(questionJpaRepository::save)
                .forEach(question -> log.info("Save question id: " + question.getId().toString()));
    }
}
