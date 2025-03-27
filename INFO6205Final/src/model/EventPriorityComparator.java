package model;

import java.util.Comparator;

public class EventPriorityComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        // 让 HIGH 的优先级排在前面, LOW 排后面
        // 可先比较 priority, 如果相同再比较startTime
        int p1 = e1.getPriority().ordinal(); // LOW=0,MEDIUM=1,HIGH=2
        int p2 = e2.getPriority().ordinal();
        // 期望HIGH排在前面 => ordinal大的排在前 => 返回负数
        // 可以写成: return Integer.compare(p2, p1); 
        int priorityCompare = p2 - p1; 
        if (priorityCompare != 0) {
            return priorityCompare;
        }
        // 若优先级相同, 按开始时间早的排在前
        return e1.getStartTime().compareTo(e2.getStartTime());
    }
}
