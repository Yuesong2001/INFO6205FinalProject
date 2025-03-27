package model;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private String eventId;               // 唯一ID
    private String title;                 // 事件名称
    private LocalDateTime startTime;      // 开始时间
    private LocalDateTime endTime;        // 结束时间
    private List<String> participants;    // 参与者列表(可以简单用List<String>)
    private PriorityLevel priority;       // 事件优先级(LOW, MEDIUM, HIGH)

    public Event(String eventId, String title, LocalDateTime startTime,
                 LocalDateTime endTime, List<String> participants, PriorityLevel priority) {
        this.eventId = eventId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = participants;
        this.priority = priority;
    }

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public List<String> getParticipants() {
		return participants;
	}

	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}

	public PriorityLevel getPriority() {
		return priority;
	}

	public void setPriority(PriorityLevel priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
	    // 随便定制一个显示格式
	    return String.format("%s (%s ~ %s)", 
	        this.title, 
	        this.startTime, 
	        this.endTime
	    );
	}


    // Getter, Setter, toString()等
    // ...
}
