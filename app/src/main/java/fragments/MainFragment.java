package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import model.Meeting;
import service.MeetingApiService;
import views.MeetingAdapter;

public class MainFragment extends Fragment {

    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;
    private RecyclerView mRecyclerView;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));  // seul
        initList();
        return view;
    }


    private void initList() {

        mMeetings = mApiService.getMeeting();  // prend la valeur de neighhbour

        mRecyclerView.setAdapter(new MeetingAdapter(mMeetings));
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
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

        mMeetings = mApiService.filterMeeting(event.day,event.month,event.room,event.roomOrDate);
        mRecyclerView.setAdapter(new MeetingAdapter(mMeetings));
    }

}
