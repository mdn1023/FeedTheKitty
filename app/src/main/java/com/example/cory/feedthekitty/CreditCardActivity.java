package com.example.cory.feedthekitty;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;

import java.util.HashMap;
import java.util.Map;

public class CreditCardActivity extends AppCompatActivity {

    private CardInputWidget mCardInputWidget;
    private Button mSubmitButton;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        mSubmitButton = (Button) findViewById(R.id.button2);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card retrieved = mCardInputWidget.getCard();
                //Toast.makeText(getBaseContext(), "Aboutta test this card", Toast.LENGTH_LONG).show();
                if(retrieved != null){
                    String uid = mAuth.getCurrentUser().getUid();
                    Map<String, Object> childUpdates = new HashMap<String, Object>();
                    HashMap<String, String> mappings = new HashMap<String, String>();
                    mappings.put("cardholder", retrieved.getName());
                    mappings.put("expmonth", retrieved.getExpMonth().toString());
                    mappings.put("expyear", retrieved.getExpYear().toString());
                    mappings.put("cardnumber", retrieved.getNumber());
                    mappings.put("cvc", retrieved.getCVC());
                    childUpdates.put("/users/" + uid + "/cardinfo/", mappings);
                    mDatabase.updateChildren(childUpdates);
                    Toast.makeText(getBaseContext(), "Card successfully read: " + retrieved.getNumber(), Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
