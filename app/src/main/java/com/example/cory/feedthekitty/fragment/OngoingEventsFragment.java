package com.example.cory.feedthekitty.fragment;

import android.util.Log;
import android.widget.Toast;

import java.util.GregorianCalendar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Akshay on 12/4/2017.
 */

public class OngoingEventsFragment extends EventListFragment {

    public OngoingEventsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        long timestamp = (new GregorianCalendar()).getTimeInMillis();
        Log.d("OngoingEventsFragment", String.valueOf(timestamp));
        return databaseReference.child("events")
                .orderByChild("timestamp").startAt(timestamp);
    }
}
