package ru.djets.tgbot.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.djets.tgbot.dao.entity.common.AbstractEntity;

import java.util.UUID;

@Entity
@Table(schema = "public", name = "tg_users")
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TgUser extends AbstractEntity<UUID> {

    @Id
    @GeneratedValue
    //  Supported only from version 6 Hibernate
    UUID id;

    @Column(name = "tg_user_id")
    long tgId;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
