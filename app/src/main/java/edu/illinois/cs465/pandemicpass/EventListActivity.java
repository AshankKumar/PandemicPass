package edu.illinois.cs465.pandemicpass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {

    private ListView eventsListView;

    private ArrayList<Event> hostingEvents;
    private ArrayList<Event> attendingEvents;

    private String userId;

    private DatabaseReference dbReferenceEvent;
    private DatabaseReference dbReferenceUserWithUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventsListView = (ListView) findViewById(R.id.listview);

        hostingEvents = new ArrayList<>();
        attendingEvents = new ArrayList<>();

        EventListNameAndDateAdapter hostEventListAdapter = new EventListNameAndDateAdapter(this, R.layout.event_list_event_name_and_date_adapter, hostingEvents);
        eventsListView.setAdapter(hostEventListAdapter);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dbReferenceEvent = FirebaseDatabase.getInstance()
                .getReference("Event");

        dbReferenceUserWithUserId = FirebaseDatabase.getInstance()
                .getReference("User").child(userId);

        dbReferenceEvent.orderByChild("hostId").equalTo(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Event e = snapshot.getValue(Event.class);
                e.id = snapshot.getKey();
                hostingEvents.add(e);
                hostEventListAdapter.notifyDataSetChanged();
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

        dbReferenceUserWithUserId.child("eventAttendance").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Event e = snapshot.getValue(Event.class);
                e.id = snapshot.getKey();
                attendingEvents.add(e);
                hostEventListAdapter.notifyDataSetChanged();
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