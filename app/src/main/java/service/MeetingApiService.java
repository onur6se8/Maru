package service;

import android.widget.EditText;

import java.util.List;

import model.Meeting;

public interface MeetingApiService {

    List<Meeting> getMeeting();                        // Ceci est une structure, l'architecture logiciel

    void  deleteMeeting(Meeting meeting);

    void  addMeetting(Meeting meeting);

    List<Meeting> filterMeeting(int day, int month, String salle, int roomOrDate);
}
