package edu.illinois.cs465.pandemicpass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MemberListAdapter extends ArrayAdapter<Member> {
    private ArrayList<Member> memberArrayList;
    private Context context;
    private int resource;
    private DatabaseReference dbReferenceUserWithUserIdMembers;

    public MemberListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Member> memberArrayList, DatabaseReference dbReferenceUserWithUserIdMembers) {
        super(context, resource, memberArrayList);
        this.context = context;
        this.resource = resource;
        this.memberArrayList = memberArrayList;
        this.dbReferenceUserWithUserIdMembers = dbReferenceUserWithUserIdMembers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).name;

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);
        nameTextView.setText(name);

        Button remove = (Button) convertView.findViewById(R.id.remove);

        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Member member = memberArrayList.get(position);
                dbReferenceUserWithUserIdMembers.child(member.id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            memberArrayList.remove(member);
                            notifyDataSetChanged();
                        }
                    }
                });

            }
        });

        return convertView;
    }
}
