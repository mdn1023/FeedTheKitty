package com.example.cory.feedthekitty.fragment;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.GregorianCalendar;

/**
 * Created by Akshay on 12/4/2017.
 */

public class PastEventsFragment extends EventListFragment {

    public PastEventsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        long timestamp = (new GregorianCalendar()).getTimeInMillis();
        Log.d("OngoingEventsFragment", String.valueOf(timestamp));
        return databaseReference.child("events")
                .orderByChild("timestamp").endAt(timestamp);
    }
}
