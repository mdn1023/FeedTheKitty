package com.example.cory.feedthekitty.models;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cory.feedthekitty.ContributeToExpense;
import com.example.cory.feedthekitty.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;


public class PostDetailActivity extends AppCompatActivity {

    int loaded = 0;
    boolean mOldEvent = false;
    TextView mTitleName, mOwnerName, mTime, mDate;
    DatabaseReference mEvent;
    ValueEventListener mPostListener;
    ArrayList<String> mListItems = new ArrayList<String>();
    ArrayAdapter<String> mAdapter;
    ListView mExpenseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Intent info = getIntent();
        final String postKey = info.getStringExtra("PostKey");
        mEvent = FirebaseDatabase.getInstance().getReference().child("events").child(postKey);

        mTitleName = findViewById(R.id.post_detail_title_name);
        mOwnerName = findViewById(R.id.post_detail_owner_name);
        mTime = findViewById(R.id.post_detail_time);
        mDate = findViewById(R.id.post_detail_date);
        mExpenseList = (ListView) findViewById(R.id.post_detail_list_view);

        mAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                mListItems);
        mExpenseList.setAdapter(mAdapter);

        mExpenseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (mOldEvent == false) {
                    //launch individual activity to contribute to this expense
                    Intent intent = new Intent(PostDetailActivity.this, ContributeToExpense.class);
                    String expense = (String) adapterView.getItemAtPosition(i);
                    String[] hold = expense.split("\\s+");
                    hold[0] = hold[0].substring(0, hold[0].length() - 1);
                    hold[1] = hold[1].substring(1, hold[1].length());
                    intent.putExtra("expense_name", hold[0]);
                    intent.putExtra("expense_price", hold[1]);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();


        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Event event = dataSnapshot.getValue(Event.class);
                if (event.timestamp < (new GregorianCalendar()).getTimeInMillis()){
                    mOldEvent = true;
                }
                // [START_EXCLUDE]

                mTitleName.setText(event.name);
                mOwnerName.setText("by: " + event.owner_name);
                if (loaded != 1) {
                    loaded = 1;
                    if (event.expenses != null) {
                        for (String key : event.expenses.keySet()) {
                            mListItems.add(key + ": $" + event.expenses.get(key));
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                return;
            }

        };
        mEvent.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;
    }
}
