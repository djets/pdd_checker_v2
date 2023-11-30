package ru.djets.tgbot.dao.entity.common;

import java.io.Serializable;

public interface CoreEntity<Id extends Serializable> {
    Id getId();

    void setId(Id id);
}
