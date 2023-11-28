package ru.djets.webclient.views.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import lombok.extern.slf4j.Slf4j;
import ru.djets.webclient.dto.AnswerDto;
import ru.djets.webclient.dto.QuestionDto;
import ru.djets.webclient.views.list.AnswerListView;

import java.util.List;

@Slf4j
public class QuestionForm extends FormLayout {

    TextArea textQuestion = new TextArea("Текст вопроса");
    TextArea description = new TextArea("Описание");
    TextField ticketNumber = new TextField("Номер билет");
    TextField numberCorrectAnswer = new TextField("Номер правильного ответа");

    VirtualList<AnswerDto> answers = new VirtualList<>();

    Button addAnswer = new Button("добавить ответ");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<QuestionDto> binder = new BeanValidationBinder<>(QuestionDto.class);

    AnswerListView answerListView;

    public QuestionForm() {
        addClassName("question-form");
        binder.bindInstanceFields(this);

        textQuestion.setHeight("10em");
        textQuestion.setReadOnly(false);
        description.setHeight("10em");
        description.setReadOnly(false);
        ticketNumber.setWidth("2em");
        ticketNumber.setReadOnly(false);
        numberCorrectAnswer.setWidth("2em");
        numberCorrectAnswer.setReadOnly(false);

        add(
                textQuestion,
                description,
                ticketNumber,
                numberCorrectAnswer,
                createServiceButtonsLayout()
        );
    }

    private void addContent(List<AnswerDto> answerDtoList) {
        if (answerDtoList != null) {
            answerListView = new AnswerListView(answerDtoList);
//            answerListView.setWidth("25em");
            add(answerListView);
        }
    }

    private Component createServiceButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new QuestionDeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new QuestionCloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new QuestionSaveEvent(this, binder.getBean()));
        }
    }

    public void setQuestionDto(QuestionDto questionDto) {
        binder.setBean(questionDto);
        if (questionDto != null) {
            log.info("select question: {}", binder.getBean().getId());
            if (questionDto.getAnswers() != null) {
                addContent(questionDto.getAnswers());
            }
        }
    }

    //Events
    public static abstract class QuestionFormEvent extends ComponentEvent<QuestionForm> {

        private QuestionDto questionDto;

        protected QuestionFormEvent(QuestionForm source, QuestionDto questionDto) {
            super(source, false);
            this.questionDto = questionDto;
        }

        public QuestionDto getQuestionDto() {
            return questionDto;
        }
    }

    public static class QuestionSaveEvent extends QuestionFormEvent {
        QuestionSaveEvent(QuestionForm source, QuestionDto questionDto) {
            super(source, questionDto);
        }
    }

    public static class QuestionDeleteEvent extends QuestionFormEvent {
        QuestionDeleteEvent(QuestionForm source, QuestionDto questionDto) {
            super(source, questionDto);
        }
    }

    public static class QuestionCloseEvent extends QuestionFormEvent {
        QuestionCloseEvent(QuestionForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<QuestionDeleteEvent> listener) {
        return addListener(QuestionDeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<QuestionSaveEvent> listener) {
        return addListener(QuestionSaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<QuestionCloseEvent> listener) {
        return addListener(QuestionCloseEvent.class, listener);

    }
}
