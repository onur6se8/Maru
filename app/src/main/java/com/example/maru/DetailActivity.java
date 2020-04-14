package com.example.maru;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import di.DI;
import model.Meeting;
import service.MeetingApiService;

public class DetailActivity extends AppCompatActivity {

    private String [] itemArray = {"Obligatoire", "Important", "Conseiller"};
    private int couleurMeeting;

    private String mHour;
    private String mMinute;
    private int h;
    private int min;

    private String mInfo;
    private String listParticipant;

    private MeetingApiService mApiService;

    @BindView(R.id.couleur)
    public ImageView mCouleur;
    @BindView(R.id.spinner)                // les interfaces graphiques
    public Spinner spin;
    @BindView(R.id.lieuReunion)
    public EditText mLieuReunion;
    @BindView(R.id.timePicker)
    public TimePicker mTimePicker;
    @BindView(R.id.sujetRéunion)
    public EditText mSujetReunion;
    @BindView(R.id.listeParticipants)
    public EditText mListParticipant;
    @BindView(R.id.buttonOK)
    public Button mButtonOK;

    @BindView(R.id.datePicker)
    public DatePicker mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        mApiService = DI.getMeetingApiService();
        this.configureToolbar();
        this.configureVariableGraphique();
    }




    private void configureVariableGraphique(){

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,itemArray);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){
                    couleurMeeting = R.drawable.couleur_rouge;
                }
                else if (position == 1){
                    couleurMeeting = R.drawable.couleur_orange;
                }
                else{
                    couleurMeeting = R.drawable.couleur_jaune;
                }

                Glide.with(mCouleur)
                        .load(couleurMeeting)
                        .apply(RequestOptions.circleCropTransform())
                        .into(mCouleur);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if ( minute < 10 ){
                    mMinute = "0" +  String.valueOf(minute);
                }
                else {
                    mMinute = String.valueOf(minute);           // en string pour afficher
                }
                mHour = String.valueOf(hourOfDay);
                min = Integer.valueOf(minute);            // en int pour calculer
                h = Integer.valueOf(hourOfDay);
            }
        });

        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mLieuReunion.getText().toString().equals("") || mSujetReunion.getText().toString().equals("") || mHour == null ){
                    Toast.makeText(getApplicationContext(), "Veuiilez remplir toute les cases.", Toast.LENGTH_LONG).show();
                }
                else {
                    mInfo = mLieuReunion.getText().toString() + " - " + mHour + "h" + mMinute + " - " + mSujetReunion.getText().toString();

                    Meeting meeting = new Meeting(
                            mApiService.getMeeting().size(),
                            mInfo,
                            mListParticipant.getText().toString(),
                            couleurMeeting,
                            h,
                            min,
                            mDatePicker.getDayOfMonth(),
                            mDatePicker.getMonth() + 1,
                            mLieuReunion.getText().toString()
                    );

                    mApiService.addMeetting(meeting);
                    finish();
                }
            }
        });
    }

    private void configureToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}

