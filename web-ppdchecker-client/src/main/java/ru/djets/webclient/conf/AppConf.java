package ru.djets.webclient.conf;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.djets.webclient.dao.repositories.QuestionJpaRepository;
import ru.djets.webclient.services.WebErrorHandler;
import ru.djets.webclient.utils.DataTemplateLoader;

@Configuration
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AppConf {

    WebErrorHandler webErrorHandler;

    QuestionJpaRepository questionJpaRepository;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .filter(webErrorHandler.errorHandler())
                .build();
    }

    @Bean
    public void loadData() {
        questionJpaRepository.deleteAll();
        DataTemplateLoader dataTemplateLoader = new DataTemplateLoader(questionJpaRepository);
        dataTemplateLoader.createQuestion(10);
    }
}
