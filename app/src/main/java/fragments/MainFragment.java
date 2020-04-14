package fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maru.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.List;
import di.DI;
import events.DeleteMeetingEvent;
import events.FilterMeetingEvent;
import events.RéinitialisationMeetingEvent;
import model.Meeting;
import service.MeetingApiService;
import views.MeetingAdapter;

public class MainFragment extends Fragment {

    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;
    private List<Meeting> filterMeetings;
    private RecyclerView mRecyclerView;

    private boolean filter = false;

    private int day;
    private int month;
    private String room;
    private int roomOrDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView)  view.findViewById(R.id.fragment_main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        initList();
        return view;
    }


    private void initList() {
        if(!filter){
            mMeetings = mApiService.getMeeting();
            mRecyclerView.setAdapter(new MeetingAdapter(mMeetings));
        }
        else {
            filterMeetings = mApiService.filterMeeting(day,month,room,roomOrDate);
            mRecyclerView.setAdapter(new MeetingAdapter(filterMeetings));
        }
    }

    @Override
    public void onResume() {        // configure par date et par salle
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {                           // gerer la vie d'un fragment
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.meeting);
        initList();
    }

    @Subscribe
    public void onFilterMeeting(FilterMeetingEvent event) {
        filter = true;
        day = event.day;
        month = event.month;
        room = event.room;
        roomOrDate = event.roomOrDate;
        initList();
    }

    @Subscribe
    public void onRetourMeeting(RéinitialisationMeetingEvent event) {
        filter = event.retour;
        initList();
    }

}
