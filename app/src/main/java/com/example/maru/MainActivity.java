package com.example.maru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.greenrobot.eventbus.EventBus;
import java.util.Collections;
import java.util.List;
import di.DI;
import events.RéinitialisationMeetingEvent;
import fragments.DialogFragment;
import fragments.MainFragment;
import model.Meeting;
import service.MeetingApiService;

public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment;

    private FloatingActionButton mFloatingActionButton;
    private List<Meeting> mMeetings;
    private MeetingApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService = DI.getMeetingApiService();
        mMeetings = mApiService.getMeeting();
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingButton);

        this.configureToolbar();

        this.configureAndShowMainFragment();

        this.configureFloatingButton();
    }

    private void configureFloatingButton() {

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    ContextCompat.startActivity(view.getContext(), intent, null);
            }
        });
    }

    private void configureAndShowMainFragment() {    // on configure le fragment comportant la list

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        if (mainFragment == null) {                  //  si il est null on le recréer
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, mainFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        // item
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  // le menu

        switch (item.getItemId()) {
            case R.id.menu_filtre:
                DialogFragment mDialogFragment = new DialogFragment();
                mDialogFragment.show(getSupportFragmentManager(), "Filter");
                return true;
            case R.id.menu_trieDate:
                configureListDate();
                return true;
            case R.id.menu_trieRoom:
                configureListRoom();
                return true;
            case R.id.menu_retour:
                EventBus.getDefault().post(new RéinitialisationMeetingEvent(false));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void configureToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureListDate() {

        Collections.sort(mMeetings);
        mainFragment.onResume();

    }

    private void configureListRoom() {

        Collections.sort(mMeetings, Meeting.ComparatorLieu);
        mainFragment.onResume();

    }
}
