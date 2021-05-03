package com.example.projetoiseaux.ui.share.time;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.share.Camera.IPictureActivity;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        (( TextView )getActivity().findViewById(R.id.hour_text)).setText(hourOfDay + ":" + minute);
        ((IPictureActivity)getActivity()).getShareFragment().addDate("-" + hourOfDay + "-" + minute);
        Log.d("mylog",((IPictureActivity)getActivity()).getShareFragment().getDate());
        // Do something with the time chosen by the user
    }
}
