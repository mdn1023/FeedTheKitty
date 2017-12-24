package com.example.cory.feedthekitty.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Akshay on 12/4/2017.
 */

public class MyEventsFragment extends EventListFragment {

    public MyEventsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-events")
                .child(getUid());
    }
}
