package edu.illinois.cs465.pandemicpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinEventCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button next;
    private TextView eventCodeTextView;
    private DatabaseReference dbReferenceEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event_code);

        dbReferenceEvent = FirebaseDatabase.getInstance().getReference("Event");
        eventCodeTextView = (TextView) findViewById(R.id.eventCode);

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.next) {
            verifyEventCodeAndRedirect();
        }
    }

    public void verifyEventCodeAndRedirect() {
        String eventCode = eventCodeTextView.getText().toString().trim();
        if (eventCode.isEmpty()) {
            return;
        }

        dbReferenceEvent.orderByChild("eventCode").equalTo(eventCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(JoinEventCodeActivity.this, "Invalid event code", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent selectMembersIntent = new Intent(JoinEventCodeActivity.this, JoinSelectMembersActivity.class);
                    for(DataSnapshot snap: snapshot.getChildren()) {
                        String eventId = snap.getKey();
                        Event e = (Event) snap.getValue(Event.class);
                        String eventName = e.eventName;
                        String eventDate = e.date;
                        selectMembersIntent.putExtra("eventId", eventId);
                        selectMembersIntent.putExtra("eventName", eventName);
                        selectMembersIntent.putExtra("eventDate", eventDate);
                        break;
                    }
                    selectMembersIntent.putExtra("eventCode", eventCode);
                    startActivity(selectMembersIntent);
                    JoinEventCodeActivity.this.finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}