package ru.djets.webclient.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import ru.djets.webclient.dao.entity.common.VersionedAbstractEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "questions")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question extends VersionedAbstractEntity<UUID> {

    @Id
    @GeneratedValue
    //  Supported only from version 6 Hibernate
    @UuidGenerator
    @Column(name = "question_id", nullable = false)
    UUID id;

    @Column(name = "text_question", nullable = false)
    String textQuestion;

    @Column(name = "path_image")
    String pathImage;

    @Column(name = "description")
    String description;

    @Column(name = "number_correct_answer")
    int numberCorrectAnswer;

    @Column(name = "ticket_number")
    int ticketNumber;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(schema = "public", name = "question_answers",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id"))
    List<Answer> answer;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Question)) {
            return false; // null or other class
        }
        Question other = (Question) obj;

        if (getId() != null) {
            return getId().equals(other.getId());
        }
        return super.equals(other);
    }
}
