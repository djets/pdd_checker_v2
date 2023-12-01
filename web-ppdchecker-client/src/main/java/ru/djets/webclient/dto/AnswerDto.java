package ru.djets.webclient.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    private String id;

    private String answerText;

    private Integer numberAnswer;

    private Long version;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswerText() {
        return this.answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getNumberAnswer() {
        return this.numberAnswer;
    }

    public void setNumberAnswer(int numberAnswer) {
        this.numberAnswer = numberAnswer;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
        if (!(obj instanceof AnswerDto)) {
            return false; // null or other class
        }
        AnswerDto other = (AnswerDto) obj;

        if (getId() != null) {
            return getId().equals(other.getId());
        }
        return super.equals(other);
    }
}
