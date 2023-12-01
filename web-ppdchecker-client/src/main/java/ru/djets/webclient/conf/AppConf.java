package ru.djets.webclient.conf;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.djets.webclient.dao.repositories.QuestionJpaRepository;
import ru.djets.webclient.services.WebErrorHandler;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AppConf {

    final WebErrorHandler webErrorHandler;

    final QuestionJpaRepository questionJpaRepository;

    @Value("${telegram.api-url}")
    String apiUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .filter(webErrorHandler.errorHandler())
                .build();
    }

//    @Bean
//    public void loadData() {
//        questionJpaRepository.deleteAll();
//        DataTemplateLoader dataTemplateLoader = new DataTemplateLoader(questionJpaRepository);
//        dataTemplateLoader.createQuestion(10);
//    }
}
