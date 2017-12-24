package com.example.cory.feedthekitty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cory.feedthekitty.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashSet;

/**
 * Created by Akshay on 12/4/2017.
 */

public class UserListActivity extends BaseActivity {
    private static final String TAG = "UserListActivity";
    Toolbar mToolbar;
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    HashSet<String> store;

    private FirebaseRecyclerAdapter<User, UserViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public UserListActivity() {}

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_all_users);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("Invite Users");
        setSupportActionBar(mToolbar);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = findViewById(R.id.user_list);
        mRecycler.setHasFixedSize(true);

        store = new HashSet<>();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(UserListActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query usersQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(usersQuery, User.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {

            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new UserViewHolder(inflater.inflate(R.layout.item_user, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(final UserViewHolder viewHolder, int position, final User model) {
                final DatabaseReference userRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = userRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                viewHolder.bindToUser(model, new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                        store.add(postKey);
                        Toast.makeText(UserListActivity.this, "Invite Sent!", Toast.LENGTH_SHORT).show();
                        viewHolder.inviteButton.setText("Invited");
                        viewHolder.inviteButton.setEnabled(false);
//                        Toast.makeText(UserListActivity.this, "Added " + postKey, Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);

        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Query getQuery(DatabaseReference databaseReference){
        return databaseReference.child("users");
    };

    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("invited", store);
        setResult(RESULT_OK, intent);
        UserListActivity.this.finish();
    }
}
