package edu.illinois.cs465.pandemicpass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Calendar;

public class HostEventFourActivity extends AppCompatActivity implements View.OnClickListener {

    private Button eventCodeButton;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog verificationDatePickerDialog;
    private Button eventDateButton;
    private Button verificationDateButton;
    private EditText eventNameText;
    private Switch vaxSwitch;
    private Switch testSwitch;

    private String eventName;
    private boolean vaxAllowed;
    private boolean testAllowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_event_four);

        initDatePicker();
        eventDateButton = findViewById(R.id.eventDatePickerButtonSummary);
        eventDateButton.setText(getTodaysDate());

        initVerificationDatePicker();
        verificationDateButton = findViewById(R.id.verificationDatePickerButtonSummary);
        verificationDateButton.setText(getTodaysDate());

        eventCodeButton = (Button) findViewById(R.id.genEventCode);
        eventCodeButton.setOnClickListener(this);

        eventNameText = (EditText) findViewById(R.id.finalEventName);
        vaxSwitch = (Switch) findViewById(R.id.finalVaxSwitch);
        testSwitch = (Switch) findViewById(R.id.finalTestSwitch);

        // Could put this in its on method
        eventName = getIntent().getExtras().getString("event_name");
        eventNameText.setText(eventName);
        vaxAllowed = getIntent().getExtras().getBoolean("vax_allowed");
        vaxSwitch.setChecked(vaxAllowed);
        testAllowed = getIntent().getExtras().getBoolean("test_allowed");
        testSwitch.setChecked(testAllowed);
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

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(month, day, year);
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

        if (id == R.id.genEventCode) {
            System.out.println("test");
            startActivity(new Intent(HostEventFourActivity.this, EventCodeActivity.class));
        }
    }
}