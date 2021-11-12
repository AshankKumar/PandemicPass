package edu.illinois.cs465.pandemicpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HostEventFourActivity extends AppCompatActivity implements View.OnClickListener {

    private Button eventCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_event_four);

        eventCodeButton = (Button) findViewById(R.id.genEventCode);
        eventCodeButton.setOnClickListener(this);
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