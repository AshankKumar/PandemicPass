// Reference Code: https://www.youtube.com/watch?v=qCoidM98zNk

package edu.illinois.cs465.pandemicpass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class HostEventTwoActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog verificationDatePickerDialog;
    private Button eventDateButton;
    private Button verificationDateButton;
    private Button nextButton;
    private String eventName;
    private boolean vaxAllowed;
    private boolean testAllowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_event_two);

        initDatePicker();
        eventDateButton = findViewById(R.id.eventDatePickerButton);
        eventDateButton.setText(getTodaysDate());

        initVerificationDatePicker();
        verificationDateButton = findViewById(R.id.verificationDatePickerButton);
        verificationDateButton.setText(getTodaysDate());

        nextButton = (Button) findViewById(R.id.hostEventTwoNextButton);
        nextButton.setOnClickListener(this);

        // Could put this in its on method
        eventName = getIntent().getExtras().getString("event_name");
        vaxAllowed = getIntent().getExtras().getBoolean("vax_allowed");
        testAllowed = getIntent().getExtras().getBoolean("test_allowed");
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(month, day, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(month, day, year);
                eventDateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private void initVerificationDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(month, day, year);
                verificationDateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        verificationDatePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int month, int day, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        return "JAN";
    }

    public void openEventDatePicker(View view) {
        datePickerDialog.show();
    }

    public void openVerificationDatePicker(View view) {
        verificationDatePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.hostEventTwoNextButton) {
//            startActivity(new Intent(HostEventTwoActivity.this, HostEventThreeActivity.class));
            Intent intent = new Intent(this, HostEventThreeActivity.class);

            intent.putExtra("event_name", eventName);
            intent.putExtra("vax_allowed", vaxAllowed);
            intent.putExtra("test_allowed", testAllowed);

            startActivity(intent);
        }
    }
}