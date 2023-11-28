package ru.djets.webclient.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.djets.webclient.services.TelegramBotRegistrationService;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ClientWebController {

    TelegramBotRegistrationService regService;

    @PostMapping("/start/")
    public String start(@RequestBody String botToken) {
        String registered = regService.registered(botToken);
        if (registered.contains("ok")) {
            return "The bot URL is registered. The bot is ready to work";
        }
        return "Bot registration failed";
    }
}
