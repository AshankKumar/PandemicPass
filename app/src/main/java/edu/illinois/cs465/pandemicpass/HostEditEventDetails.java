package edu.illinois.cs465.pandemicpass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HostEditEventDetails extends AppCompatActivity implements View.OnClickListener {

    private Button eventCodeButton;
    private DatePickerDialog datePickerDialog;
    private Button eventDateButton;
    private EditText eventNameText;
    private EditText eventLocationEditText;
    private EditText eventDescriptionEditText;
    private Switch vaxSwitch;
    private Switch testSwitch;

    private String eventName;
    private String eventLocation;
    private String eventDescription;
    private boolean vaxAllowed;
    private boolean testAllowed;
    private int eventMonth;
    private int eventDay;
    private int eventYear;
    private DateFormat dateFormat;

    private DatabaseReference dbReferenceEvent;
    private DatabaseReference dbReferenceUser;

    private DateFormat dateFormatOnlyDate;
    private DateFormat dateFormatOnlyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_edit_event_details);

        dbReferenceEvent = FirebaseDatabase.getInstance()
                .getReference("Event");

        dbReferenceUser = FirebaseDatabase.getInstance()
                .getReference("User");

        initDatePicker();
        eventDateButton = findViewById(R.id.eventDatePickerButtonEdit);
        eventDateButton.setText(getTodaysDate());

        eventCodeButton = (Button) findViewById(R.id.finalizeEventDetails);
        eventCodeButton.setOnClickListener(this);

        eventNameText = (EditText) findViewById(R.id.EditEventName);
        eventLocationEditText = (EditText) findViewById(R.id.EditEventLocationEditText);
        eventDescriptionEditText = (EditText) findViewById(R.id.EditEventDescriptionEditText);
        vaxSwitch = (Switch) findViewById(R.id.EditVaxSwitch);
        testSwitch = (Switch) findViewById(R.id.EditTestSwitch);

        dateFormat = DateFormat.getDateTimeInstance(
                DateFormat.LONG, DateFormat.LONG,
                Locale.getDefault());

        initExtras();

        dateFormatOnlyDate = DateFormat.getDateInstance();
        dateFormatOnlyTime = DateFormat.getTimeInstance(DateFormat.SHORT);
    }

    private void initExtras() {
        eventName = getIntent().getExtras().getString("event_name");
        eventNameText.setText(eventName);
        vaxAllowed = getIntent().getExtras().getBoolean("vax_allowed");
        vaxSwitch.setChecked(vaxAllowed);
        testAllowed = getIntent().getExtras().getBoolean("test_allowed");
        testSwitch.setChecked(testAllowed);

        eventMonth = getIntent().getExtras().getInt("event_month");
        eventDay = getIntent().getExtras().getInt("event_day");
        eventYear = getIntent().getExtras().getInt("event_year");
        String date = makeDateString(eventMonth, eventDay, eventYear);
        eventDateButton.setText(date);

        eventLocation = getIntent().getExtras().getString("event_location");
        eventLocationEditText.setText(eventLocation);
        eventDescription = getIntent().getExtras().getString("event_description");
        eventDescriptionEditText.setText(eventDescription);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(month, day, year);
                eventDateButton.setText(date);

                eventYear = year;
                eventMonth = month;
                eventDay = day;
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
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


    @Override
    public void onClick(View v) {

    }
}