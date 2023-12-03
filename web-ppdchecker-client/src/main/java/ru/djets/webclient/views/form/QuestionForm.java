package ru.djets.webclient.views.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import lombok.extern.slf4j.Slf4j;
import ru.djets.webclient.dto.AnswerDto;
import ru.djets.webclient.dto.QuestionDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class QuestionForm extends FormLayout {
    TextArea textQuestion = new TextArea("Текст вопроса");
    TextArea description = new TextArea("Описание");
    TextField pathImage = new TextField("Ссылка на картинку");
    IntegerField numberQuestion = new IntegerField("Номер вопроса");
    IntegerField numberCorrectAnswer = new IntegerField("Номер правильного ответа");
    IntegerField ticketNumber = new IntegerField("Номер билета");

    Grid<AnswerDto> answerDtoGrid = new Grid<>(AnswerDto.class, false);

    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");

    Binder<QuestionDto> binder = new BeanValidationBinder<>(QuestionDto.class);

    Button addAnswerButton = new Button("Добавить ответ");

    public QuestionForm() {
        addClassName("question-form");
        binder.bindInstanceFields(this);
        getBlockAnswers();
        add(
                textQuestion,
                description,
                pathImage,
                getBlockQuestion(),
                createQuestionsButtonsLayout()
        );
    }

    private Component getBlockQuestion() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(
                numberQuestion,
                ticketNumber,
                numberCorrectAnswer
        );
        return horizontalLayout;
    }

    private void getBlockAnswers() {
        Grid.Column<AnswerDto> numberAnswerColumn = answerDtoGrid.addColumn(AnswerDto::getNumberAnswer)
                .setHeader("N").setSortable(true).setWidth("20px").setFlexGrow(0);
        Grid.Column<AnswerDto> answerDtoColumn =
                answerDtoGrid.addColumn(AnswerDto::getAnswerText).setHeader("Ответы");
        answerDtoGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        answerDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        answerDtoColumn.setAutoWidth(false);
        answerDtoColumn.setFlexGrow(1);

        Editor<AnswerDto> editor = answerDtoGrid.getEditor();

        Grid.Column<AnswerDto> editAnswerDtoColumn = answerDtoGrid.addComponentColumn(answerDto -> {
            Button editButton = new Button("edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen()) {
                    editor.cancel();
                }
                answerDtoGrid.getEditor().editItem(answerDto);
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        Binder<AnswerDto> answerDtoBinder = new Binder<>(AnswerDto.class);
        editor.setBinder(answerDtoBinder);
        editor.setBuffered(true);

        IntegerField numberAnswerField = new IntegerField();
        numberAnswerField.setWidthFull();
        answerDtoBinder.forField(numberAnswerField)
                .asRequired("Field NotNull")
                .bind(AnswerDto::getNumberAnswer,
                        AnswerDto::setNumberAnswer);
        numberAnswerColumn.setEditorComponent(numberAnswerField);

        TextField answerTextField = new TextField();
        answerTextField.setWidthFull();
        answerDtoBinder.forField(answerTextField)
                .asRequired("Ответ не может быть пустым")
                .bind(AnswerDto::getAnswerText, AnswerDto::setAnswerText);
        answerDtoColumn.setEditorComponent(answerTextField);

        Button saveButton = new Button("save", e -> editor.save());

        Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editAnswerDtoColumn.setEditorComponent(actions);
    }

    private Component createQuestionsButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        addAnswerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new QuestionDeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new QuestionCloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        addAnswerButton.addClickListener(event -> {
            List<AnswerDto> copyAnswersDto = new ArrayList<>(binder.getBean().getAnswers());
            AnswerDto answerDto = new AnswerDto();
            answerDto.setNumberAnswer(copyAnswersDto.size() + 1);
            copyAnswersDto.add(answerDto);
            binder.getBean().setAnswers(copyAnswersDto);
            updateAnswer();
        });
        HorizontalLayout horizontalLayout = new HorizontalLayout(save, delete, close, addAnswerButton);
        horizontalLayout.setPadding(true);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return horizontalLayout;
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
                updateAnswer();
                add(answerDtoGrid);
            }
        }
    }

    private void updateAnswer() {
        answerDtoGrid.setItems(binder.getBean().getAnswers());
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
