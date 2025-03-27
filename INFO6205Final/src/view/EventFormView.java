package view;

import controller.NavigationController;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.DataStore;
import model.Event;
import model.PriorityLevel;
import model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventFormView extends VBox {

    public EventFormView(User user, DataStore dataStore, NavigationController navController) {
        setSpacing(10);
        setAlignment(Pos.CENTER);

        TextField titleField = new TextField();
        titleField.setPromptText("Event Title");

        TextField startField = new TextField("2025-04-01T10:00"); // 简化，实际可用DatePicker+Time
        TextField endField   = new TextField("2025-04-01T11:00");

        TextField participantsField = new TextField();
        participantsField.setPromptText("Participant1,Participant2");

        ComboBox<PriorityLevel> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(PriorityLevel.LOW, PriorityLevel.MEDIUM, PriorityLevel.HIGH);
        priorityBox.setValue(PriorityLevel.MEDIUM);

        Button saveBtn = new Button("Save");

        saveBtn.setOnAction(e -> {
            String eventId = UUID.randomUUID().toString();
            String title = titleField.getText();
            LocalDateTime startTime = LocalDateTime.parse(startField.getText());
            LocalDateTime endTime   = LocalDateTime.parse(endField.getText());
            String participants = participantsField.getText();
            PriorityLevel pl = priorityBox.getValue();

            // 创建事件, 添加到DataStore
            Event newEvent = new Event(eventId, title, startTime, endTime,
                    List.of(participants.split(",")), pl);
            dataStore.addEvent(user.getUserId(), newEvent);

            // 返回上一页面
            navController.popPane();
            
            System.out.println("Save Event => " + newEvent.getTitle() 
            + ", startTime=" + newEvent.getStartTime() 
            + ", userId=" + user.getUserId());
        
        
        });

        getChildren().addAll(titleField, startField, endField, participantsField, priorityBox, saveBtn);
    }
}
