package edu.illinois.cs465.pandemicpass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditMemberActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public class Person {
        private String name;
        private String status;

        public Person(String name, String status) {
            this.name = name;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class PersonListAdapter extends ArrayAdapter {
        private static final String TAG = "PersonListAdapter";

        private Context mContext;
        private int mResource;
        private int lastPosition = -1;

        public PersonListAdapter(@NonNull Context context, int resource, ArrayList<Person> objects) {
            super(context, resource, objects);
            this.mContext = context;
            mResource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            String name = ((Person) getItem(position)).getName();
            String status = ((Person) getItem(position)).getStatus();

            Person person = new Person(name, status);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
            TextView tvStatus = (TextView) convertView.findViewById(R.id.textView2);

            tvName.setText(name);
            tvStatus.setText(status);

            return convertView;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);
        mAuth = FirebaseAuth.getInstance();

        String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        ListView mListView = (ListView) findViewById(R.id.nameStatusListView);
        ArrayList<Person> peopleList = new ArrayList<>();
        mAuth.getCurrentUser();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User");

        userReference.orderByChild("email").equalTo(currentEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                peopleList.clear();
                GenericTypeIndicator<ArrayList<Member>> t = new GenericTypeIndicator<ArrayList<Member>>() {};
                ArrayList<Member> memberArrayList = dataSnapshot.getValue(t);
                for(Member member:memberArrayList){
                    peopleList.add(new Person(member.name, "good"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: " ,error.getMessage());
            }
        });
        PersonListAdapter adapter = new PersonListAdapter(this, R.layout.adapter_view_layout_name_status, peopleList);
        mListView.setAdapter(adapter);

    }
}