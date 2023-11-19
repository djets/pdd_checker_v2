package ru.djets.tgbot.service.factory;

public interface BotObjectFactory<C,S,I> {
    public C create(I i, S s);
}
