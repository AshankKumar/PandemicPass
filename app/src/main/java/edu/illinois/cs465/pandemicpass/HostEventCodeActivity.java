package edu.illinois.cs465.pandemicpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HostEventCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button homeButton;
    private TextView eventCodeTextView;
    private String eventCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_code_host);

        homeButton = (Button) findViewById(R.id.hostEventHomeButton);
        homeButton.setOnClickListener(this);

        eventCodeTextView = (TextView) findViewById(R.id.eventCode);

        eventCode = getIntent().getExtras().getString("event_code");
        eventCodeTextView.setText(eventCode);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.hostEventHomeButton) {
            startActivity(new Intent(HostEventCodeActivity.this, HomeScreenActivity.class));
        }
    }
}