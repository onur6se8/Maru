package com.example.maru;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import di.DI;
import fragments.MainFragment;
import model.Meeting;
import service.DummyMeetingApiService;
import service.ListMeeting;
import service.MeetingApiService;
import views.MeetingAdapter;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingWithSuccess() {

        List<Meeting> meetings = service.getMeeting();
        List<Meeting> expectedMeeting = ListMeeting.regenerateMeeting();

        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeeting.toArray()));
        assertEquals(3,meetings.size());
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeeting().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeeting().contains(meetingToDelete));

        List<Meeting> meetingDelete = service.getMeeting();
        assertEquals(2, meetingDelete.size());
    }

    @Test
    public void addMeeting () {

        Meeting meeting = new Meeting(
                4,
                "Réunion C - 10h00 - Yoshi",
                "maxim@lamzone.com,vivian@lamzone.com,...",
                R.drawable.couleur_jaune,
                10,
                0,
                4,
                2,
                "Réunion C"
        );
        service.addMeetting(meeting);

        List<Meeting> meetingAdd = service.getMeeting();
        assertEquals(4, meetingAdd.size());
    }

    @Test
    public void filterDateMeeting() {

        List<Meeting> meetings = service.filterMeeting(24,2,"Réunion C",0);   //  0

        assertEquals(1,meetings.size());
        assertEquals( service.getMeeting().get(2), meetings.get(0));
    }

    @Test
    public void filterRoomMeeting() {

        List<Meeting> meetings = service.filterMeeting(24,2,"Réunion C",1);  // 1

        assertEquals(1,meetings.size());
        assertEquals( service.getMeeting().get(2), meetings.get(0));
    }
}