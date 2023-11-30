package ru.djets.webclient.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.djets.webclient.dao.entity.BotSettings;
import ru.djets.webclient.dao.services.BotSettingsService;
import ru.djets.webclient.dao.services.QuestionService;
import ru.djets.webclient.dto.QuestionDto;
import ru.djets.webclient.services.TelegramBotRegistrationService;
import ru.djets.webclient.views.form.QuestionForm;

import java.util.List;

@PageTitle("Questions")
@Route("")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class QuestionListView extends VerticalLayout {

    final QuestionService questionService;

    final BotSettingsService botSettingsService;

    final TelegramBotRegistrationService registrationService;

    final Grid<QuestionDto> grid = new Grid<>(QuestionDto.class, false);

    final TextField filterText = new TextField();

    final TextField botName = new TextField();

    final TextField botToken = new TextField();

    final Select<Integer> select = new Select<>();
    QuestionForm form;

    public QuestionListView(
            QuestionService questionService,
            BotSettingsService botSettingsService,
            TelegramBotRegistrationService registrationService
    ) {
        this.questionService = questionService;
        this.botSettingsService = botSettingsService;
        this.registrationService = registrationService;
        addClassName("questions-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getBotLayout(), getToolbar(), getContent());
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

        select.setItems(questionService.findAll()
                .stream()
                .map(QuestionDto::getTicketNumber)
                .distinct()
                .sorted()
                .toList()
        );
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Фильтр по тексту вопроса...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateQuestions());
        filterText.setWidth("25em");

        botName.setPlaceholder("bot name...");
        botToken.setPlaceholder("bot token...");

        select.setEmptySelectionAllowed(true);
        select.addValueChangeListener(e -> updateQuestions());

        Button addQuestion = new Button("Добавить вопрос");
        addQuestion.addClickListener(click -> addQuestion());

        HorizontalLayout toolbar = new HorizontalLayout(select, filterText, addQuestion);
        toolbar.addClassName("toolbar");
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);
        return toolbar;
    }

    private HorizontalLayout getBotLayout() {
        HorizontalLayout botLayout = new HorizontalLayout();

        Button registrationButton = new Button("Зарегистрировать бота");
        registrationButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        registrationButton.addClickListener(e -> {
            String registered;
            BotSettings botSettings = botSettingsService.findByBotName(botName.getValue());
            if (botSettings != null) {
                botSettings.setBotToken(botToken.getValue());
                registered = registrationService.registered(botSettingsService.save(botSettings));
            } else {
                registered = registrationService.registered(
                        botSettingsService.save(
                                new BotSettings()
                                        .setBotName(botName.getValue())
                                        .setBotToken(botToken.getValue())));
            }
            Notification.show(registered);
        });
        botLayout.add(registrationButton, botName, botToken);
        botLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        return botLayout;
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
        if (select.getValue() != null) {
            log.info("filtered ticket: {}", select.getValue());
            List<QuestionDto> allQuestionDtoByTicketNumber = questionService.getAllByTicketNumber(select.getValue());
            grid.setItems(allQuestionDtoByTicketNumber
                    .stream()
                    .filter(questionDto -> questionDto.getTextQuestion().contains(filterText.getValue()))
                    .toList());
        } else {
            List<QuestionDto> allQuestionDto = questionService.findAll();
            grid.setItems(allQuestionDto
                    .stream()
                    .filter(questionDto -> questionDto.getTextQuestion().contains(filterText.getValue()))
                    .toList());
        }
    }
}

