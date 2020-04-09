package com.example.maru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import di.DI;
import events.DeleteMeetingEvent;
import fragments.DialogFragment;
import fragments.MainFragment;
import model.Meeting;
import service.MeetingApiService;

import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity {

    private final static int IDENTIFIANT_BOITE_UN  = 0;
    private final static int IDENTIFIANT_BOITE_DEUX  = 1;

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

    private void configureAndShowMainFragment() {

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, mainFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureToolbar() {   //
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
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
