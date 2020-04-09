package events;

import model.Meeting;

public class FilterMeetingEvent {

    public int day;
    public int month;
    public String room;
    public int roomOrDate;

    public FilterMeetingEvent(int day, int month, String room, int roomOrDate){

        this.day = day;
        this.month = month;
        this.room = room;
        this.roomOrDate = roomOrDate;
    }
}
