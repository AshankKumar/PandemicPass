package edu.illinois.cs465.pandemicpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EventCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_code);

        homeButton = (Button) findViewById(R.id.hostEventHomeButton);
        homeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.hostEventHomeButton) {
            startActivity(new Intent(EventCodeActivity.this, HomeScreenActivity.class));
        }
    }
}