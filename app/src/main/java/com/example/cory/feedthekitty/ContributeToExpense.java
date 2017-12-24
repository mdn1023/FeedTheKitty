package com.example.cory.feedthekitty;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cory.feedthekitty.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ContributeToExpense extends AppCompatActivity {


    TextView mTitleName, mPrice;

    TextView seekBarValue;
    DatabaseReference mDatabase;
    Button mSubmitButton;
    FirebaseAuth mAuth;
    String cardnumber;
    int barVal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute_to_expense);

        mTitleName = findViewById(R.id.contribute_header);
        mTitleName.setText(getIntent().getStringExtra("expense_name") );
        mSubmitButton = findViewById(R.id.submit_expense_addition);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(1);
        seekBar.setMax( Integer.parseInt(getIntent().getStringExtra("expense_price")) );
        seekBarValue = (TextView)findViewById(R.id.contribution_counter);
        seekBarValue.setText("0");

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Payment of $"+barVal+" received!", Toast.LENGTH_SHORT).show();
                ContributeToExpense.this.finish();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress - 1;
                progress = progress + 1;
                barVal = progress;
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        String uid = mAuth.getCurrentUser().getUid();

        DatabaseReference cardinfo = mDatabase.child("users").child(uid);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Map<String, Object> cardinfo = dataSnapshot.getValue(User.class).cardinfo;
                if (cardinfo != null) {
                    ContributeToExpense.this.makeSavedCardDialog((String) cardinfo.get("cardnumber"));
                }
                else {
                    ContributeToExpense.this.makeAddCardDialog();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                return;
            }
        };
        cardinfo.addListenerForSingleValueEvent(postListener);

    }

    @Override
    public void onBackPressed(){
        ContributeToExpense.this.finish();
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    public void makeSavedCardDialog(String cardnumber){
        if (cardnumber.length() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to pay with your saved card? (XXXX-XXXX-XXXX-" + cardnumber.substring(cardnumber.length() - 4, cardnumber.length()) + ")")
                    .setTitle("Use Saved Card");

            builder.setPositiveButton(R.string.dialog_yes_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getBaseContext(), "Card successfully added as payment", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton(R.string.dialog_no_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog.Builder addcard = new AlertDialog.Builder(ContributeToExpense.this);
                    addcard.setMessage("Please enter a credit card under Settings -> Add credit card before making a payment")
                            .setTitle("Error making payment");
                    addcard.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    addcard.create().show();
                }
            });

            builder.create().show();
        }
    }
    public void makeAddCardDialog() {
        AlertDialog.Builder addcard = new AlertDialog.Builder(ContributeToExpense.this);
        addcard.setMessage("Please enter a credit card under Settings -> Add credit card before making a payment")
                .setTitle("Error making payment");
        addcard.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        addcard.create().show();
    }
}
