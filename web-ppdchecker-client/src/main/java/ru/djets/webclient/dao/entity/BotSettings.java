package ru.djets.webclient.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.djets.webclient.dao.entity.common.VersionedAbstractEntity;

import java.util.UUID;

@Entity
@Table(schema = "public", name = "bot_settings")
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BotSettings extends VersionedAbstractEntity<UUID> {

    @Id
    @GeneratedValue
    //  Supported only from version 6 Hibernate
    UUID id;

    String botName, botToken, botPath, status;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setId(UUID uuid) {
        this.id = uuid;
    }
}
