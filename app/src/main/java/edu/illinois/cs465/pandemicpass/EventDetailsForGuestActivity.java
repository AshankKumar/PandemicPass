package edu.illinois.cs465.pandemicpass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EventDetailsForGuestActivity extends AppCompatActivity implements View.OnClickListener {

    private String userId;
    private String eventId;

    private TextView eventNameTextView;
    private TextView hostTextView;
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView locationTextView;
    private TextView requirementsTextView;
    private TextView descriptionTextView;
    private TextView proportionOfGuestsApprovedTextView;
    private ListView attendingMemberListView;
    private Button editPartyMembers;
    private Button updateStatuses;

    private DatabaseReference dbReferenceEventWithEventIdGuestList;
    private ArrayList<Guest> attendingMemberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_for_guest);

        if (getIntent() != null && getIntent().getExtras() != null
                && getIntent().hasExtra("eventCode")) {
            eventId = getIntent().getExtras().getString("eventId");
        }
        else {
            return;
        }

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        eventNameTextView = (TextView) findViewById(R.id.eventName);
        hostTextView = (TextView) findViewById(R.id.host);
        dateTextView = (TextView) findViewById(R.id.date);
        timeTextView = (TextView) findViewById(R.id.time);
        locationTextView = (TextView) findViewById(R.id.location);
        requirementsTextView = (TextView) findViewById(R.id.requirements);
        descriptionTextView = (TextView) findViewById(R.id.description);

        proportionOfGuestsApprovedTextView = (TextView) findViewById(R.id.proportionOfGuestsApproved);

        attendingMemberListView = (ListView) findViewById(R.id.attendingMemberList);

        editPartyMembers = (Button) findViewById(R.id.editPartyMembers);
        editPartyMembers.setOnClickListener(this);

        updateStatuses = (Button) findViewById(R.id.updateStatuses);
        updateStatuses.setOnClickListener(this);

        dbReferenceEventWithEventIdGuestList = FirebaseDatabase.getInstance().getReference("Event").child(eventId).child("guestList");
        attendingMemberList = new ArrayList<Guest>();

        UserAttendingMemberListAdapter attendingMemberListAdapter = new UserAttendingMemberListAdapter(this, R.layout.attending_member_list_adapter, attendingMemberList);
        attendingMemberListView.setAdapter(attendingMemberListAdapter);

        dbReferenceEventWithEventIdGuestList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Guest guest = snapshot.getValue(Guest.class);

                if (guest.userId == userId) {
                    attendingMemberList.add(guest);
                    attendingMemberListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Guest guest = snapshot.getValue(Guest.class);
                int index = attendingMemberList.indexOf(guest);

                if (index != -1) {
                    attendingMemberList.set(index, guest);
                    attendingMemberListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Guest guest = snapshot.getValue(Guest.class);
                int index = attendingMemberList.indexOf(guest);

                if (index != -1) {
                    attendingMemberList.remove(guest);
                    attendingMemberListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.editPartyMembers) {
            //TODO
        }

        else if (id == R.id.updateStatuses) {
            //TODO
        }
    }
}