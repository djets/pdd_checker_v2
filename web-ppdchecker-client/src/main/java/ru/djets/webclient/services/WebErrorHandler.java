package ru.djets.webclient.services;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public interface WebErrorHandler {

    ExchangeFilterFunction errorHandler();
}
