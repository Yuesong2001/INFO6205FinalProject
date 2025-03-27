package controller;

import model.DataStore;
import model.Event;
import model.PriorityLevel;
import model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class EventController {
    private DataStore dataStore;

    public EventController(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void createEvent(User user, String title, LocalDateTime start, LocalDateTime end,
                            String participants, PriorityLevel priority) {
        String eventId = UUID.randomUUID().toString();
        // 简化处理参与者
        Event newEvent = new Event(eventId, title, start, end,
                                   Arrays.asList(participants.split(",")), 
                                   priority);
        dataStore.addEvent(user.getUserId(), newEvent);
    }

    public void searchEventsByTitle(String title) {
        // 直接调用 dataStore 的方法
        // 后续可以更新UI, 这里仅演示
        dataStore.searchEventsByTitle(title).forEach(System.out::println);
    }
}
