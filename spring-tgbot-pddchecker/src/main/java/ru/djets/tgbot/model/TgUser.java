package ru.djets.tgbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TgUser {
    @Id
    @GeneratedValue
    //  Supported only from version 6 Hibernate
    UUID id;

    @Column(name = "tg_id")
    long tgId;
}
