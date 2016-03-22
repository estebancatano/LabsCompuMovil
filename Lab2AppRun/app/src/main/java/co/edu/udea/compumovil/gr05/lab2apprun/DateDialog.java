package co.edu.udea.compumovil.gr05.lab2apprun;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by joluditru on 19/03/2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Establece la fecha actual en el DatePickerFragment
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    // Callback to DatePickerActivity.onDateSet() to update the UI
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        ((DatePickerDialog.OnDateSetListener) getActivity()).onDateSet(view, year, monthOfYear, dayOfMonth);
    }
}
