package com.example.cory.feedthekitty;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cory.feedthekitty.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    EditText mDisplayName, mEmailAddress, mPassword;
    Button mSubmitButton;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
// ...

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private void focusEmail(){
        View focusView = null;
        focusView = mEmailAddress;
        focusView.requestFocus();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();
        mDisplayName = (EditText) findViewById(R.id.display_name_text_view);
        mEmailAddress = (EditText) findViewById(R.id.enter_email_field);
        mPassword = (EditText) findViewById(R.id.enter_password_field);

        mSubmitButton = (Button) findViewById(R.id.submit_create_account);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mAuthTask != null) {
//                    return;
//                }

                // Reset errors.
                mEmailAddress.setError(null);
                mPassword.setError(null);

                // Store values at the time of the login attempt.
                String email = mEmailAddress.getText().toString();
                String password = mPassword.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
                if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                    mPassword.setError(getString(R.string.error_invalid_password));
                    focusView = mPassword;
                    cancel = true;
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(email)) {
                    mEmailAddress.setError(getString(R.string.error_field_required));
                    focusView = mEmailAddress;
                    cancel = true;
                } else if (!isEmailValid(email)) {
                    mEmailAddress.setError(getString(R.string.error_invalid_email));
                    focusView = mEmailAddress;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    // Show a progress spinner, and kick off a background task to
                    // perform the user login attempt.
                    //showProgress(true);
                    //            mAuthTask = new UserLoginTask(email, password);
                    //            mAuthTask.execute((Void) null);
                    //Log.i(TAG, "trying to make contact");
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Log.i(TAG, "we here bois too");
                            if (task.isSuccessful()) {
                                //Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(mDisplayName.getText().toString()).build();
                                user.updateProfile(profileUpdates);
                                Toast.makeText(CreateAccount.this, "Thanks for signing up, " + mDisplayName.getText().toString(), Toast.LENGTH_SHORT).show();
                                String uid = user.getUid();
                                writeNewUser(uid, mDisplayName.getText().toString(), CreateAccount.this.mEmailAddress.getText().toString());
                                CreateAccount.this.finish();
                            } else {
                                Toast.makeText(CreateAccount.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                focusEmail();
                            }
                        }
                    });
                }
                //end onClick
            }
            //end setOnCLickListener
        });
    }
}
