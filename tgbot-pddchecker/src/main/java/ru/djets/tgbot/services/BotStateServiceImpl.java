package ru.djets.tgbot.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.djets.tgbot.dto.QuestionDto;

import java.util.HashMap;
import java.util.Map;

@Service
@Setter
@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BotStateServiceImpl implements BotStateService {

    Map<String, Integer> ticketSelectedMap = new HashMap<>();

    Map<String, QuestionDto> questionSelectedMap = new HashMap<>();
}
