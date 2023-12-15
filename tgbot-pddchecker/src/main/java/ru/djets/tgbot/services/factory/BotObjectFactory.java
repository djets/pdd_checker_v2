package ru.djets.tgbot.services.factory;

public interface BotObjectFactory<C,S,I> {
    C create(I i, S s);
}
