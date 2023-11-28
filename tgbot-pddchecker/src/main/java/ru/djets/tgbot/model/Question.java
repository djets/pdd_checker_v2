package ru.djets.tgbot.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "question")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {

    @Id
    @GeneratedValue
    //  Supported only from version 6 Hibernate
    @UuidGenerator
    UUID id;

    @Column(name = "text_question", length = 3000)
    String textQuestion;

    @Column(name = "path_image", length = 3000)
    String pathImage;

    @Column(name = "description", length = 3000)
    String description;

    @Column(name = "correct_answer")
    int numberCorrectAnswer;

    @Column(name = "ticket_number")
    int ticketNumber;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    List<Answer> answer;
}
