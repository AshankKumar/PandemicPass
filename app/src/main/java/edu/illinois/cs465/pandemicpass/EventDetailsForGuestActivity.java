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
import com.google.firebase.database.ValueEventListener;

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
    private int totalGuests;
    private int guestsApproved;

    private DatabaseReference dbReferenceEventWithEventId;
    private DatabaseReference dbReferenceEventWithEventIdGuestList;
    private ArrayList<Guest> guestsInUserGroupAttending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_for_guest);

        if (getIntent() != null && getIntent().getExtras() != null
                && getIntent().hasExtra("eventId")) {
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

        dbReferenceEventWithEventId = FirebaseDatabase.getInstance().getReference("Event").child(eventId);
        dbReferenceEventWithEventIdGuestList = dbReferenceEventWithEventId.child("guestList");

        dbReferenceEventWithEventId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Event e = snapshot.getValue(Event.class);
                    loadEventDetails(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        totalGuests = 0;
        guestsApproved = 0;

        guestsInUserGroupAttending = new ArrayList<Guest>();

        UserAttendingMemberListAdapter attendingMemberListAdapter = new UserAttendingMemberListAdapter(this, R.layout.attending_member_list_adapter, guestsInUserGroupAttending);
        attendingMemberListView.setAdapter(attendingMemberListAdapter);

        dbReferenceEventWithEventIdGuestList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Guest guest = snapshot.getValue(Guest.class);

                if (guest.userId.equals(userId)) {
                    guestsInUserGroupAttending.add(guest);
                    attendingMemberListAdapter.notifyDataSetChanged();
                }
                totalGuests += 1;
                if (guest.approvalStatus.equals("Approved")) {
                    guestsApproved += 1;
                    updateProportionOfGuestsApprovedTextView();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Guest guest = snapshot.getValue(Guest.class);
                int index = guestsInUserGroupAttending.indexOf(guest);

                if (index != -1) {
                    String previousApprovalStatus = guestsInUserGroupAttending.get(index).approvalStatus;
                    String newApprovalStatus = guest.approvalStatus;

                    if (!previousApprovalStatus.equals(newApprovalStatus)) {
                        if (newApprovalStatus.equals("Approved")) {
                            guestsApproved += 1;
                            updateProportionOfGuestsApprovedTextView();
                        }
                        else if (previousApprovalStatus.equals("Approved")) {
                            guestsApproved -= 1;
                            updateProportionOfGuestsApprovedTextView();
                        }
                    }

                    guestsInUserGroupAttending.set(index, guest);
                    attendingMemberListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Guest guest = snapshot.getValue(Guest.class);
                int index = guestsInUserGroupAttending.indexOf(guest);

                if (index != -1) {
                    String previousApprovalStatus = guestsInUserGroupAttending.get(index).approvalStatus;

                    if (previousApprovalStatus.equals("Approved")) {
                        guestsApproved -= 1;
                        updateProportionOfGuestsApprovedTextView();
                    }

                    guestsInUserGroupAttending.remove(guest);
                    totalGuests -= 1;
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

    private void loadEventDetails(Event e) {
        eventNameTextView.setText(e.eventName);
        hostTextView.setText(e.hostName);
        dateTextView.setText(e.date);
        timeTextView.setText(e.time);
        locationTextView.setText(e.location);
        ArrayList<String> requirements = new ArrayList<String>();

        if (e.acceptVaccinationRecord) {
            requirements.add("Vaccination Record");
        }
        if (e.acceptTestResult) {
            requirements.add("Test Result");
        }

        if (requirements.size() == 0) {
            requirementsTextView.setText("None");
        }
        else if (requirements.size() == 1) {
            requirementsTextView.setText(requirements.get(0));
        }
        else {
            String reqStr = requirements.get(0);
            for (int i = 1; i < requirements.size(); i += 1) {
                reqStr += ", " + requirements.get(i);
            }
            requirementsTextView.setText(reqStr);
        }

        descriptionTextView.setText(e.description);

        updateProportionOfGuestsApprovedTextView();
    }

    private void updateProportionOfGuestsApprovedTextView() {
        proportionOfGuestsApprovedTextView = (TextView) findViewById(R.id.proportionOfGuestsApproved);
        proportionOfGuestsApprovedTextView.setText(String.valueOf(guestsApproved) + " of " + String.valueOf(totalGuests) + " guests approved.");

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