package edu.illinois.cs465.pandemicpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HostApproveAndDeny extends AppCompatActivity implements View.OnClickListener {

    private TextView guestNameTextView;
    private TextView guestApprovalStatusTextView;
    private TextView guestVaccineStatus;
    private TextView guestTestingStatus;
    private TextView testingStatusTextTextView;
    private TextView vaccineStatusTextTextView;

    private Button viewVaccineButton;
    private Button viewTestButton;
    private Button acceptButton;
    private Button denyButton;

    private DatabaseReference dbReferenceEvent;
    private DatabaseReference dbReferenceUser;

    private String guestId;
    private String eventId;
    private String memberId;
    private String userId;
    private String guestKey;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_approve_and_deny);

        userId = getIntent().getExtras().getString("guest_id");
        eventId = getIntent().getExtras().getString("event_id");
        memberId = getIntent().getExtras().getString("member_id");
        guestKey = getIntent().getExtras().getString("guestKey");

        guestNameTextView = (TextView) findViewById(R.id.hostViewGuestName);
        guestApprovalStatusTextView = (TextView) findViewById(R.id.hostViewGuestapproval_status);
        guestVaccineStatus = (TextView) findViewById(R.id.hostViewGuestvaccine_status);
        guestTestingStatus = (TextView) findViewById(R.id.hostViewGuesttesting_status);

        testingStatusTextTextView = (TextView) findViewById(R.id.hostViewTestingStatusText);
        vaccineStatusTextTextView = (TextView) findViewById(R.id.HostViewVaccineStatusText);

        viewVaccineButton = (Button) findViewById(R.id.hostViewGuestVaccine);
        viewVaccineButton.setOnClickListener(this);

        viewTestButton = (Button) findViewById(R.id.hostViewGuestTest);
        viewTestButton.setOnClickListener(this);

        acceptButton = (Button) findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener(this);

        denyButton = (Button) findViewById(R.id.denyButton);
        denyButton.setOnClickListener(this);

        dbReferenceEvent = FirebaseDatabase.getInstance()
                .getReference("Event");

        dbReferenceUser = FirebaseDatabase.getInstance()
                .getReference("User");

        Log.d("TESTINGUGH", "userId: " + userId);
        Log.d("TESTINGUGH", "memberId: " + memberId);

        getEventDetails();
    }

    private void getEventDetails() {
        DatabaseReference dbReferenceEventWithEventId = dbReferenceEvent.child(eventId);
        dbReferenceEventWithEventId = FirebaseDatabase.getInstance().getReference("Event").child(eventId);

        dbReferenceEventWithEventId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    event = snapshot.getValue(Event.class);
                    renderScreen();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void renderScreen() {
        if (!event.acceptTestResult) {
            testingStatusTextTextView.setVisibility(View.GONE);
            guestTestingStatus.setVisibility(View.GONE);
            viewTestButton.setVisibility(View.GONE);

        } else {

        }

        if (!event.acceptVaccinationRecord) {
            vaccineStatusTextTextView.setVisibility(View.GONE);
            guestVaccineStatus.setVisibility(View.GONE);
            viewVaccineButton.setVisibility(View.GONE);
        } else {

        }
    }

    @Override
    public void onClick(View v) {

    }
}