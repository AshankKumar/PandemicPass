package edu.illinois.cs465.pandemicpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventDetailsForHostActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView EventNameTextView;
    private TextView EventLocationTextView;
    private TextView EventDescriptionTextView;
    private TextView VaxTextView;
    private TextView TestTextView;
    private Button ReturnHomeButton;
    private Button ViewGustsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_for_host);

        EventNameTextView = (TextView) findViewById(R.id.HostViewEventName);
        EventLocationTextView = (TextView) findViewById(R.id.HostViewEventLocation);
        EventDescriptionTextView = (TextView) findViewById(R.id.HostViewEventDescription);
        VaxTextView = (TextView) findViewById(R.id.HostViewEventVax);
        TestTextView = (TextView) findViewById(R.id.HostViewEventTest);

        ReturnHomeButton = (Button) findViewById(R.id.HostViewHomeButton);
        ReturnHomeButton.setOnClickListener(this);

        ViewGustsButton = (Button) findViewById(R.id.HostViewGuestsButton);
        ViewGustsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.HostViewHomeButton) {
            startActivity(new Intent(EventDetailsForHostActivity.this, HomeScreenActivity.class));
        }
    }
}