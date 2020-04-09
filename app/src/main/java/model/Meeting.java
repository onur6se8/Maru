package model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Comparator;
import java.util.Objects;

public class Meeting implements Comparable<Meeting>{

    private Integer id;
    private String info;
    private String member;
    private int couleurAvatar;
    private int h;
    private int min;
    private int day;
    private int month;
    private String room;

    public Meeting (Integer id, String info, String member, int couleurAvatar, int h, int min, int day, int month, String room){

        this.id = id;
        this.info = info;
        this.member = member;
        this.couleurAvatar = couleurAvatar;
        this.h = h;
        this.min = min;
        this.day = day;
        this.month = month;
        this.room = room;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public  String getMember() { return member; }

    public int getCouleurAvatar() {
        return couleurAvatar;
    }

    public int getH(){ return h; }

    public int getMin() { return  min; }

    public int getDay() { return day;}

    public int getMonth() { return month; }

    public String getRoom() { return room; }

    @Override
    public int compareTo( Meeting meeting){
        if(month - meeting.month == 0 ){
            if ( day - meeting.day == 0){
                if ( h - meeting.h == 0){
                    if ( min - meeting.min == 0) {
                        return 0;
                    }
                    else {
                        return min - meeting.min;
                    }
                }
                else {
                    return h - meeting.h;
                }
            }
            else {
                return day - meeting.day;
            }
        }
        else {

            return month - meeting.month;
        }
    }

    public static Comparator<Meeting> ComparatorLieu = new Comparator<Meeting>() {

        @Override
        public int compare(Meeting e1, Meeting e2) {
            return e1.getInfo().compareTo(e2.getInfo());
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
