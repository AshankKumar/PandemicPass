package edu.illinois.cs465.pandemicpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HostEventThreeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button nextButton;
    private String eventName;
    private boolean vaxAllowed;
    private boolean testAllowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_event_three);

        nextButton = (Button) findViewById(R.id.hostEventThreeNextButton);
        nextButton.setOnClickListener(this);

        // Could put this in its on method
        eventName = getIntent().getExtras().getString("event_name");
        vaxAllowed = getIntent().getExtras().getBoolean("vax_allowed");
        testAllowed = getIntent().getExtras().getBoolean("test_allowed");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.hostEventThreeNextButton) {
//            startActivity(new Intent(HostEventThreeActivity.this, HostEventFourActivity.class));
            Intent intent = new Intent(this, HostEventFourActivity.class);

            intent.putExtra("event_name", eventName);
            intent.putExtra("vax_allowed", vaxAllowed);
            intent.putExtra("test_allowed", testAllowed);

            startActivity(intent);
        }
    }
}