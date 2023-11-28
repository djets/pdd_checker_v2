package ru.djets.webclient.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.djets.webclient.dao.services.AnswerService;
import ru.djets.webclient.dto.AnswerDto;
import ru.djets.webclient.views.form.AnswerForm;

import java.util.List;

@org.springframework.stereotype.Component
@Setter
@Getter
@Slf4j
public class AnswerListView extends Div {
    Grid<AnswerDto> gridAnswer = new Grid<>(AnswerDto.class, false);

    @Autowired
    private AnswerService answerService;

    List<AnswerDto> answerDtoList;

    AnswerForm form;

    public AnswerListView(List<AnswerDto> answerDtoList) {
        this.answerDtoList = answerDtoList;
        addClassName("answers-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getContent());
        updateAnswers();
        closeEditor();
    }

    private Component getContent() {
        VerticalLayout content = new VerticalLayout(gridAnswer, form);
        content.setFlexGrow(2, gridAnswer);
        content.setFlexGrow(1, form);
        content.addClassNames("content-answers");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new AnswerForm();
        form.setWidth("25em");
    }

    private void configureGrid() {
        gridAnswer.addClassName("answerDto-grid");
//        gridAnswer.setSizeFull();
//        gridAnswer.addColumn("answerText");
        gridAnswer.addColumn(AnswerDto::getAnswerText).setHeader("Ответ");
//        gridAnswer.getColumns().forEach(col -> col.setAutoWidth(true));

        gridAnswer.asSingleSelect().addValueChangeListener(event ->
                editAnswer(event.getValue()));;
    }

//    private HorizontalLayout getToolbar() {
//        Button addAnswer = new Button("Добавить ответ");
//        addAnswer.addClickListener(click -> addAnswer());
//
//        HorizontalLayout toolbar = new HorizontalLayout(addAnswer);
//        toolbar.addClassName("toolbar");
//        return toolbar;

//    }

    private void editAnswer(AnswerDto answerDto) {
        if (answerDto == null) {
            closeEditor();
        } else {
            form.setAnswerDto(answerDto);
            form.setVisible(true);
            addClassName("editing-answer");
        }
    }

    private void closeEditor() {
        form.setAnswerDto(null);
        form.setVisible(false);
        removeClassName("editing-answer");
    }

    private void addAnswer() {
        gridAnswer.asSingleSelect().clear();
        editAnswer(new AnswerDto());
    }

    private void saveAnswer(AnswerForm.AnswerSaveEvent event) {
        answerService.save(event.getAnswerDto());
        updateAnswers();
        closeEditor();
    }

    private void deleteAnswer(AnswerForm.AnswerDeleteEvent event) {
        answerService.deleteAnswer(event.getAnswerDto());
        updateAnswers();
        closeEditor();
    }

    private void updateAnswers() {
        log.info("сколько: {}", answerDtoList.size());
        gridAnswer.setItems(answerDtoList);
    }
}
