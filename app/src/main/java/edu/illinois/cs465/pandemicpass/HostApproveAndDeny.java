package edu.illinois.cs465.pandemicpass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class HostApproveAndDeny extends AppCompatActivity {

    private String guestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_approve_and_deny);

        guestId = getIntent().getExtras().getString("guest_id");

        Log.d("DEBUG", guestId);
    }
}