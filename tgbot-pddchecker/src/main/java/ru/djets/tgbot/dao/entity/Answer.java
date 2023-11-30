package ru.djets.tgbot.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import ru.djets.tgbot.dao.entity.common.VersionedAbstractEntity;

import java.util.UUID;

@Entity
@Table(schema = "public",name = "answers")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer extends VersionedAbstractEntity<UUID> {

    @Id
    @GeneratedValue
    //  Supported only from version 6 Hibernate
    @UuidGenerator
    @Column(name = "answer_id", nullable = false)
    UUID id;

    @Column(name = "answer_text", nullable = false)
    String answerText;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
