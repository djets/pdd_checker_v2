package ru.djets.tgbot.service.factory;

public interface BotObjectFactory<C,S,I> {
    C create(I i, S s);
}
