package fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maru.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import events.FilterMeetingEvent;


public class DialogFragment extends androidx.fragment.app.DialogFragment {

    public DialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View mView = inflater.inflate(R.layout.fragment_dialog, null);

        DatePicker picker = (DatePicker) mView.findViewById(R.id.datePicker);
        EditText salle = (EditText) mView.findViewById(R.id.sujetRéunion);

        builder.setView(mView)
                .setTitle("Filter")
                .setPositiveButton("Apply", (dialog, id) -> {

                    if(picker == null && salle.getText().toString().equals("") ){
                        Toast.makeText(mView.getContext(), "Veuiilez remplir tout les cases.", Toast.LENGTH_LONG).show();
                    }
                    else if(salle.getText().toString().equals("")){

                        EventBus.getDefault().post(new FilterMeetingEvent( picker.getDayOfMonth(), picker.getMonth() + 1, salle.getText().toString(), 0));
                        DialogFragment.this.getDialog().cancel();
                    }
                    else if(picker == null){

                        EventBus.getDefault().post(new FilterMeetingEvent( picker.getDayOfMonth(), picker.getMonth() + 1, salle.getText().toString(), 1));
                        DialogFragment.this.getDialog().cancel();
                    }
                    else {

                        EventBus.getDefault().post(new FilterMeetingEvent( picker.getDayOfMonth(), picker.getMonth() + 1, salle.getText().toString(), 2));
                        DialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> DialogFragment.this.getDialog().cancel());
        return builder.create();
    }

}
