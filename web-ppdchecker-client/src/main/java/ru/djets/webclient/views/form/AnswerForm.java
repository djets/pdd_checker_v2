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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import ru.djets.webclient.dto.AnswerDto;

public class AnswerForm extends FormLayout {

//    VirtualList<AnswerDto> answers = new VirtualList<>();

    TextArea answerText = new TextArea("Текст ответа");

    Binder<AnswerDto> binderAnswer = new BeanValidationBinder<>(AnswerDto.class);

    Button saveAnswer = new Button("Save");
    Button deleteAnswer = new Button("Delete");
    Button closeAnswer = new Button("Cancel");

//    private ComponentRenderer<Component, AnswerDto> answerDtoRenderer = new ComponentRenderer<>(
//            answerDto -> {
//                HorizontalLayout answersLayout = new HorizontalLayout();
//                answersLayout.setMargin(true);
//
//                TextArea answer = new TextArea("Ответ");
//
////                Binder<AnswerDto> answerDtoBinder = new Binder<>();
////                answerDtoBinder.bindInstanceFields(answerDto);
//
//                String answerText = answerDto.getAnswerText();
//                answer.setReadOnly(false);
//                answer.setValue(answerText);
//
//                VerticalLayout answerLayout = new VerticalLayout();
//                answerLayout.add(answerText);
//
//                answersLayout.add(answerLayout);
//                return answersLayout;
//            });

    public AnswerForm() {
        addClassName("answers-form");
        binderAnswer.bindInstanceFields(this);

        add(answerText, createServiceButtonsLayout());
    }

    private Component createServiceButtonsLayout() {
        saveAnswer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteAnswer.addThemeVariants(ButtonVariant.LUMO_ERROR);
        closeAnswer.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        saveAnswer.addClickShortcut(Key.ENTER);
        closeAnswer.addClickShortcut(Key.ESCAPE);

        saveAnswer.addClickListener(event -> validateAndSave());
        deleteAnswer.addClickListener(event -> fireEvent(new AnswerDeleteEvent(this, binderAnswer.getBean())));
        closeAnswer.addClickListener(event -> fireEvent(new AnswerCloseEvent(this)));

        binderAnswer.addStatusChangeListener(e -> saveAnswer.setEnabled(binderAnswer.isValid()));
        return new HorizontalLayout(deleteAnswer, closeAnswer);
    }

    private void validateAndSave() {
        if (binderAnswer.isValid()) {
            fireEvent(new AnswerSaveEvent(this, binderAnswer.getBean()));
        }
    }

    public void setAnswerDto(AnswerDto answerDto) {
        binderAnswer.setBean(answerDto);
    }

    //Events
    public static abstract class AnswerFormEvent extends ComponentEvent<AnswerForm> {

        private AnswerDto answerDto;

        protected AnswerFormEvent(AnswerForm source, AnswerDto answerDto) {
            super(source, false);
            this.answerDto = answerDto;
        }

        public AnswerDto getAnswerDto() {
            return answerDto;
        }
    }

    public static class AnswerSaveEvent extends AnswerForm.AnswerFormEvent {
        AnswerSaveEvent(AnswerForm source, AnswerDto answerDto) {
            super(source, answerDto);
        }
    }

    public static class AnswerDeleteEvent extends AnswerForm.AnswerFormEvent {
        AnswerDeleteEvent(AnswerForm source, AnswerDto answerDto) {
            super(source, answerDto);
        }
    }

    public static class AnswerCloseEvent extends AnswerForm.AnswerFormEvent {
        AnswerCloseEvent(AnswerForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<AnswerDeleteEvent> listener) {
        return addListener(AnswerDeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<AnswerSaveEvent> listener) {
        return addListener(AnswerSaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<AnswerCloseEvent> listener) {
        return addListener(AnswerCloseEvent.class, listener);

    }
}
