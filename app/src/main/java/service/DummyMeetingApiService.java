package service;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Meeting;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings = ListMeeting.generateMeeting();

    @Override
    public List<Meeting> getMeeting() {
        return mMeetings;
    }

    @Override
    public void deleteMeeting( Meeting meeting) {
        mMeetings.remove(meeting);
    }

    @Override
    public void addMeetting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public List<Meeting> filterMeeting(int day, int month, String salle, int roomOrDate) {
        List<Meeting> filter = new ArrayList<>();
        for(int i = 0; i < mMeetings.size(); i++)
        {
            if ( roomOrDate == 0){
                if(mMeetings.get(i).getDay() == day && mMeetings.get(i).getMonth() == month) // si true alors
                {
                    filter.add(mMeetings.get(i));
                }
            }
            else if(roomOrDate == 1){
                if(mMeetings.get(i).getRoom() == salle)
                {
                    filter.add(mMeetings.get(i));
                }
            }
            else {
                if(mMeetings.get(i).getDay() == day && mMeetings.get(i).getMonth() == month && mMeetings.get(i).getRoom() == salle)
                {
                    filter.add(mMeetings.get(i));
                }
            }
        }
        return filter;
    }
}
