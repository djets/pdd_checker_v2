package ru.djets.tgbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    String id, questionText, description, pathImage;

    int ticketNumber, numberCorrectAnswer;

    List<String> answers;
}
