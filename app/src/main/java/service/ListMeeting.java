package service;

import com.example.maru.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Meeting;

public abstract class ListMeeting {

    public static List<Meeting> regenerateMeeting (){

            List<Meeting> meetingList  = Arrays.asList (
            new Meeting(1, "Réunion A - 14h00 - Peach", "maxim@lamzone.com,vivian@lamzone.com,...", R.drawable.couleur_rouge, 14, 0, 14, 3, "Réunion A"),
            new Meeting(2, "Réunion B - 16h00 - Mario", "paul@lamzone.com,vivian@lamzone.com,....", R.drawable.couleur_orange, 16, 0, 24,4,"Réunion B"),
            new Meeting(3, "Réunion C - 19h00 - Luigi", "amandine@lamzone.com,luc@lamzone.com,...", R.drawable.couleur_orange, 19, 0,24,2,"Réunion C")
            );

            return meetingList;
    }


    public static List<Meeting> generateMeeting() {
        return new ArrayList<>(regenerateMeeting());
    }
}
