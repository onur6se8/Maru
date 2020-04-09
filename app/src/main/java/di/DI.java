package di;

import service.DummyMeetingApiService;
import service.MeetingApiService;

public class DI {

    private static MeetingApiService service = new DummyMeetingApiService();


    public static MeetingApiService getMeetingApiService() {
        return service;
    }


    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}
