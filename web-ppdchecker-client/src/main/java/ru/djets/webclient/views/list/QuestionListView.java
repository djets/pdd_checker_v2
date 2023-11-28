package ru.djets.webclient.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.djets.webclient.dao.services.QuestionService;
import ru.djets.webclient.dto.QuestionDto;
import ru.djets.webclient.views.form.QuestionForm;

@PageTitle("Questions")
@Route("")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class QuestionListView extends VerticalLayout {

    final QuestionService questionService;

    final Grid<QuestionDto> grid = new Grid<>(QuestionDto.class, false);

    final TextField filterText = new TextField();
    private QuestionForm form;

    public QuestionListView(
            QuestionService questionService) {
        this.questionService = questionService;
        addClassName("questions-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateQuestions();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new QuestionForm();
        form.setWidth("50em");
        form.addSaveListener(this::saveQuestion);
        form.addDeleteListener(this::deleteQuestion);
        form.addCloseListener(e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassName("questionDto-grid");
        grid.setSizeFull();
//        grid.setColumns();
        grid.addColumn(QuestionDto::getTextQuestion).setHeader("Текст вопроса");
        grid.addComponentColumn(questionDto -> createHasPhotoIcon(
                questionDto.getPathImage() != null)).setHeader("Картинка");
//        grid.addColumn(questionDto -> questionDto.getAnswers()
//                .stream()
//                .map(answerDto ->
//                        questionDto.getAnswers().indexOf(answerDto) + 1 +
//                                ". " + answerDto.getAnswerText())
//                .collect(Collectors.joining("\n"))
//        ).setHeader("Ответы");
        grid.addColumn(QuestionDto::getNumberCorrectAnswer).setHeader("Правильный ответ");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editQuestion(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Фильтр по тексту вопроса...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateQuestions());
        filterText.setWidth("25em");

        Button addQuestion = new Button("Добавить вопрос");
        addQuestion.addClickListener(click -> addQuestion());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addQuestion);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void editQuestion(QuestionDto questionDto) {
        if (questionDto == null) {
            closeEditor();
        } else {
            form.setQuestionDto(questionDto);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setQuestionDto(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addQuestion() {
        grid.asSingleSelect().clear();
        editQuestion(new QuestionDto());
    }

    private void saveQuestion(QuestionForm.QuestionSaveEvent event) {
        questionService.save(event.getQuestionDto());
        updateQuestions();
        closeEditor();
    }

    private void deleteQuestion(QuestionForm.QuestionDeleteEvent event) {
        questionService.deleteQuestion(event.getQuestionDto());
        updateQuestions();
        closeEditor();
    }

    private Icon createHasPhotoIcon(boolean hasPhoto) {
        Icon icon;
        if (hasPhoto) {
            icon = createIcon(VaadinIcon.CHECK, "Yes");
            icon.getElement().getThemeList().add("badge success");
        } else {
            icon = createIcon(VaadinIcon.CLOSE_SMALL, "No");
            icon.getElement().getThemeList().add("badge error");
        }
        return icon;
    }

    private Icon createIcon(VaadinIcon vaadinIcon, String label) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        // Accessible label
        icon.getElement().setAttribute("aria-label", label);
        // Tooltip
        icon.getElement().setAttribute("title", label);
        return icon;
    }

    private void updateQuestions() {
        grid.setItems(questionService
                .searchQuestionByTextQuestionContaining(filterText.getValue()));
    }
}

