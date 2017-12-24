package com.example.cory.feedthekitty;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cory.feedthekitty.models.Event;
import com.example.cory.feedthekitty.models.User;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Akshay on 12/4/2017.
 */

public class UserViewHolder extends RecyclerView.ViewHolder{
    public TextView userNameView;
    public TextView userEmailView;
    public Button inviteButton;
//    public ImageView owner_photo;

    public UserViewHolder(View itemView) {
        super(itemView);

        userNameView = itemView.findViewById(R.id.user_name);
        userEmailView = itemView.findViewById(R.id.user_email);
        inviteButton = itemView.findViewById(R.id.invite_button);
//        owner_photo = itemView.findViewById(R.id.owner_photo);
    }

    public void bindToUser(User user, View.OnClickListener inviteClickListener) {
        userNameView.setText(user.name);
        userEmailView.setText(user.email);
        inviteButton.setOnClickListener(inviteClickListener);
//        bodyView.setText(post.body);

//        starView.setOnClickListener(starClickListener);
    }
}
