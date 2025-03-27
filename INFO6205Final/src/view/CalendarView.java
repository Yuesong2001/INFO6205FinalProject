package view;

import controller.NavigationController;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.DataStore;
import model.Event;
import model.User;

import java.time.LocalDate;
import java.util.List;

public class CalendarView extends VBox {

    private User currentUser;
    private DataStore dataStore;
    private NavigationController navController;

    public CalendarView(User user, DataStore dataStore, NavigationController navController) {
        this.currentUser = user;
        this.dataStore = dataStore;
        this.navController = navController;

        setSpacing(10);
        setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome, " + user.getUsername());

        // Choose Date
        DatePicker datePicker = new DatePicker(LocalDate.now());

        // List all the event sorted by priority
        Button showEventsBtn = new Button("Show My Events");
        ListView<Event> eventListView = new ListView<>();

        showEventsBtn.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            List<Event> events = dataStore.getUserEventsByDay(user.getUserId(), selectedDate);
            
            // —— Test Information ——
            System.out.println("------ Show My Events ------");
            System.out.println("Selected date: " + selectedDate);
            System.out.println("Current user ID: " + user.getUserId());
            System.out.println("Found events: " + events.size());
            for (Event ev : events) {
                System.out.println("Event: " + ev.getTitle() + " " + ev.getStartTime());
            }
            System.out.println("----------------------------");

            eventListView.getItems().setAll(events);
        });

        // Add Buttons here 
        Button addEventBtn = new Button("Add Event");
        addEventBtn.setOnAction(e -> {
            navController.pushPane(new EventFormView(currentUser, dataStore, navController));
        });
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            navController.popPane();
        });

        getChildren().addAll(welcomeLabel, datePicker, showEventsBtn, eventListView, addEventBtn,logoutBtn);
    }
}
