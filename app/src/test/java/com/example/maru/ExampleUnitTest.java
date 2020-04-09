package com.example.maru;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import di.DI;
import model.Meeting;
import service.DummyMeetingApiService;
import service.ListMeeting;
import service.MeetingApiService;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
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
                2
        );
        service.addMeetting(meeting);

        List<Meeting> meetingAdd = service.getMeeting();
        assertEquals(4, meetingAdd.size());

    }

}