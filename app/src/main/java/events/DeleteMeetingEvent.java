package events;

import model.Meeting;

public class DeleteMeetingEvent {

    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
