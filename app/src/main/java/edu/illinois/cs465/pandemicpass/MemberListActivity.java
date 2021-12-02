package edu.illinois.cs465.pandemicpass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MemberListActivity extends AppCompatActivity implements View.OnClickListener {

    private String userId;

    private DatabaseReference dbReferenceUserWithUserId;
    private DatabaseReference dbReferenceUserWithUserIdMembers;

    private EditText memberNameEditText;
    private Button addMember;
    private ListView memberListView;
    private ArrayList<Member> memberArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dbReferenceUserWithUserId = FirebaseDatabase.getInstance()
                .getReference("User").child(userId);
        dbReferenceUserWithUserIdMembers = dbReferenceUserWithUserId.child("members");

        memberNameEditText = (EditText) findViewById(R.id.memberName);

        addMember = (Button) findViewById(R.id.addMember);
        addMember.setOnClickListener(this);

        memberArrayList = new ArrayList<Member>();

        memberListView = (ListView) findViewById(R.id.memberList);

        MemberListAdapter memberListAdapter = new MemberListAdapter(this, R.layout.member_list_adapter, memberArrayList, dbReferenceUserWithUserIdMembers, dbReferenceUserWithUserId, userId);

        memberListView.setAdapter(memberListAdapter);

        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Member member = (Member) adapterView.getItemAtPosition(i);
                Intent profileIntent = new Intent(MemberListActivity.this, MemberActivity.class);
                profileIntent.putExtra("member", member);
                startActivity(profileIntent);
            }
        });

        dbReferenceUserWithUserIdMembers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Member member = snapshot.getValue(Member.class);
                member.id = snapshot.getKey();
                memberArrayList.add(member);
                memberListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Member member = snapshot.getValue(Member.class);
                member.id = snapshot.getKey();
                int index = memberArrayList.indexOf(member);

                if (index != -1) {
                    memberArrayList.set(index, member);
                    memberListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Member member = snapshot.getValue(Member.class);
                member.id = snapshot.getKey();
                memberArrayList.remove(member);
                memberListAdapter.notifyDataSetChanged();
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

        if (id == R.id.addMember) {
            addMemberToMemberList();
        }
    }

    private void addMemberToMemberList() {
        String memberName = memberNameEditText.getText().toString().trim();
        if (!memberName.isEmpty()) {
            Member member = new Member(memberName, "", "");
            dbReferenceUserWithUserIdMembers.push().setValue(member);
        }
    }
}