package ru.djets.tgbot.conf;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.djets.tgbot.repositories.QuestionJpaRepository;
import ru.djets.tgbot.services.WebhookBotService;
import ru.djets.tgbot.utils.DataTemplateLoader;

@Configuration
@EnableConfigurationProperties(BotConfig.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AppConfig {

    BotConfig botConfig;

    QuestionJpaRepository questionJpaRepository;

    @Bean
    public SetWebhook getSetWebhook() {
        return SetWebhook.builder().url(botConfig.getBotPath()).build();
    }

    @Bean
    public WebhookBotService getWebhookBotService(SetWebhook setWebhook){
        return new WebhookBotService(setWebhook);
    }

    @Bean
    public void dataTemplateLoader () {
        DataTemplateLoader dataTemplateLoader = new DataTemplateLoader(questionJpaRepository);
        dataTemplateLoader.createQuestion(10);
    }
}
