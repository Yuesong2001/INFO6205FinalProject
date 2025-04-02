package model;

import java.time.LocalDate;
import java.util.*;

public class DataStore {

    // 模拟数据库, 所有用户
    private Map<String, User> userMap = new HashMap<>();
    // 模拟数据库, 所有事件
    private Map<String, Event> eventMap = new HashMap<>();
    // 每个用户 -> (某一天 -> 优先队列(事件))
    private Map<String, Map<LocalDate, PriorityQueue<Event>>> userDailyEvents = new HashMap<>();

    public DataStore() {
        User defaultUser = new User("u001", "alice", "123456");
        userMap.put(defaultUser.getUserId(), defaultUser);
    }

    // ========= 用户相关操作 =========
    public User findUserByUsername(String username) {
        for (User u : userMap.values()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public void addUser(User user) {
        userMap.put(user.getUserId(), user);
    }

    // ========= 事件相关操作 =========
    public void addEvent(String userId, Event event) {
        eventMap.put(event.getEventId(), event);

        // 把事件放到对应用户对应日期的优先队列里
        LocalDate date = event.getStartTime().toLocalDate();
        userDailyEvents.putIfAbsent(userId, new HashMap<>());

        Map<LocalDate, PriorityQueue<Event>> dailyMap = userDailyEvents.get(userId);
        dailyMap.putIfAbsent(date, new PriorityQueue<>(new EventPriorityComparator()));

        // 往对应日期的PriorityQueue里添加事件
        dailyMap.get(date).add(event);
        
        System.out.println("AddEvent: userId=" + userId + ", eventTitle=" + event.getTitle());
    }

    public Event getEventById(String eventId) {
        return eventMap.get(eventId);
    }

    // 搜索事件（示例：按标题在eventMap中遍历匹配）
    public List<Event> searchEventsByTitle(String title) {
        List<Event> result = new ArrayList<>();
        for (Event e : eventMap.values()) {
            if (e.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(e);
            }
        }
        return result;
    }

    // 获取某用户在某天的事件 (按优先级排好序)
    public List<Event> getUserEventsByDay(String userId, LocalDate day) {
        Map<LocalDate, PriorityQueue<Event>> dailyMap = userDailyEvents.get(userId);
        if (dailyMap == null) return Collections.emptyList();

        PriorityQueue<Event> pq = dailyMap.get(day);
        if (pq == null) return Collections.emptyList();
        
        

        // PriorityQueue遍历时并不会破坏原顺序，可以把它copy出来
        return new ArrayList<>(pq);
    }
    
    // 新增，删除事件
    public boolean removeEvent(String userId, String eventId) {
        // 确认事件是否存在
        Event eventToRemove = eventMap.get(eventId);
        if (eventToRemove == null) {
            System.out.println("RemoveEvent: Event not found, eventId=" + eventId);
            return false;
        }
        
        // Get the date of the event
        LocalDate eventDate = eventToRemove.getStartTime().toLocalDate();
        
        // Check if the user has any events
        Map<LocalDate, PriorityQueue<Event>> userEvents = userDailyEvents.get(userId);
        if (userEvents == null) {
            System.out.println("RemoveEvent: No events for userId=" + userId);
            return false;
        }
        
        // Check if the user has events on that date
        PriorityQueue<Event> eventsOnDate = userEvents.get(eventDate);
        if (eventsOnDate == null) {
            System.out.println("RemoveEvent: No events for date=" + eventDate);
            return false;
        }
        
        // 从priority queue中删除event
        boolean removed = eventsOnDate.removeIf(event -> event.getEventId().equals(eventId));
        
        // If the priority queue is now empty, remove it from the map
        if (eventsOnDate.isEmpty()) {
            userEvents.remove(eventDate);
            
            // If the user has no more events, remove their entry from userDailyEvents
            if (userEvents.isEmpty()) {
                userDailyEvents.remove(userId);
            }
        }
        
        // 从eventMap中删除
        if (removed) {
            eventMap.remove(eventId);
            System.out.println("RemoveEvent: Successfully removed eventId=" + eventId + ", title=" + eventToRemove.getTitle());
        } else {
            System.out.println("RemoveEvent: Event not found in user's events, eventId=" + eventId);
        }
        
        return removed;
    }
}

