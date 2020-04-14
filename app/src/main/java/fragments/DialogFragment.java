package fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maru.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import events.DeleteMeetingEvent;
import events.FilterMeetingEvent;
import model.Meeting;


public class DialogFragment extends AppCompatDialogFragment {               // la boite de dialogue

    private DatePicker picker;
    private EditText salle;

    private Boolean pickerChanged = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert);
        LayoutInflater inflater = getActivity().getLayoutInflater();     // on appel les interface graphiques
        View view = inflater.inflate(R.layout.fragment_dialog, null);

        builder.setView(view)
                .setTitle("Filtrer")
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if(!pickerChanged && salle.getText().toString().equals("") ){
                            Toast.makeText(getContext(), "Filtrer par date et/ou par lieu.", Toast.LENGTH_LONG).show();
                        }
                        else if(salle.getText().toString().equals("")){

                            EventBus.getDefault().post(new FilterMeetingEvent( picker.getDayOfMonth(), picker.getMonth() + 1, salle.getText().toString(), 0));
                        }
                        else if(!pickerChanged){

                            EventBus.getDefault().post(new FilterMeetingEvent( picker.getDayOfMonth(), picker.getMonth() + 1, salle.getText().toString(), 1));
                        }
                        else {

                            EventBus.getDefault().post(new FilterMeetingEvent( picker.getDayOfMonth(), picker.getMonth() + 1, salle.getText().toString(), 2));
                        }
                    }
                })
                .setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                       DialogFragment.this.getDialog().cancel();
                    }
                });
        picker = view.findViewById(R.id.datePickerDialog);
        salle = view.findViewById(R.id.dialogSalle);

        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                pickerChanged = true;
            }
        });

        return builder.create();
    }
}
