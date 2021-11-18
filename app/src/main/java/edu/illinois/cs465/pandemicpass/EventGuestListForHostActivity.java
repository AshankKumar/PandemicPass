package edu.illinois.cs465.pandemicpass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EventGuestListForHostActivity extends AppCompatActivity {

    private ListView guestsListView;

    private ArrayList<Guest> guestsArrayList;

    private DatabaseReference dbReferenceEvent;

    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_guest_list_for_host);

        guestsListView = (ListView) findViewById(R.id.guestListView);

        guestsArrayList = new ArrayList<>();

        dbReferenceEvent = FirebaseDatabase.getInstance()
                .getReference("Event");

        GuestListGuestNameAdapter guestListAdapter = new GuestListGuestNameAdapter(this, R.layout.guest_list_adapter, guestsArrayList);
        guestsListView.setAdapter(guestListAdapter);

        eventId = getIntent().getExtras().getString("event_id");

        dbReferenceEvent.child(eventId).child("guestList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Guest guest = snapshot.getValue(Guest.class);
                guest.userId = snapshot.getKey();
                guestsArrayList.add(guest);
                guestListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}