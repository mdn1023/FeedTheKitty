package com.example.cory.feedthekitty;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardRequirements;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import java.util.Arrays;

public class AddExpense extends AppCompatActivity {

    public int LOAD_PAYMENT_DATA_REQUEST_CODE = 7;

    int hold = 0;
    EditText mExpenseName;
    TextView mMoneySum;
    Button mPlusOne, mPlusFive, mPlusTen, mPlusFifty, mReset, mSubmitExpense;
    private PaymentsClient mPaymentsClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);


        // get views
        mExpenseName = (EditText) findViewById(R.id.expense_name);

        mMoneySum = (TextView) findViewById(R.id.money_counter);
        mMoneySum.setText("0.00");

        mPlusOne = (Button) findViewById(R.id.one_dollar);
        mPlusFive = (Button) findViewById(R.id.five_dollars);
        mPlusTen = (Button) findViewById(R.id.ten_dollars);
        mPlusFifty = (Button) findViewById(R.id.fifty_dollars);

        mReset = (Button) findViewById(R.id.reset_amount);
        mSubmitExpense = (Button) findViewById(R.id.submit_expense);

        mPaymentsClient = Wallet.getPaymentsClient(this, new Wallet.WalletOptions.Builder().setEnvironment(WalletConstants.ENVIRONMENT_TEST).build());
        // set click listeners
        mPlusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hold += 1;
                mMoneySum.setText(""+hold+".00");
//                Toast.makeText(getBaseContext(), hold+"", Toast.LENGTH_SHORT).show();
            }
        });

        mPlusFifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hold += 50;
                mMoneySum.setText(""+hold+".00");
//                Toast.makeText(getBaseContext(), hold+"", Toast.LENGTH_SHORT).show();
            }
        });

        mPlusTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hold += 10;
                mMoneySum.setText(""+hold+".00");
//                Toast.makeText(getBaseContext(), hold+"", Toast.LENGTH_SHORT).show();
            }
        });

        mPlusFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hold += 5;
                mMoneySum.setText(""+hold+".00");
//                Toast.makeText(getBaseContext(), hold+"", Toast.LENGTH_SHORT).show();
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hold = 0;
                mMoneySum.setText(""+hold+".00");
                //Toast.makeText(getBaseContext(), hold+"", Toast.LENGTH_SHORT).show();
            }
        });

        mSubmitExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get name of event + cost and add it to ListView in TestActivity.
                Toast.makeText(getBaseContext(), "Expense recorded!", Toast.LENGTH_SHORT).show();
//                PaymentDataRequest request = createPaymentDataRequest();
//                if (request != null) {
//                    AutoResolveHelper.resolveTask(mPaymentsClient.loadPaymentData(request), AddExpense.this, LOAD_PAYMENT_DATA_REQUEST_CODE);
//                }
//                submitClick();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("name", mExpenseName.getText().toString());
                //Toast.makeText(getBaseContext(), mExpenseName.getText(), Toast.LENGTH_SHORT).show();
                returnIntent.putExtra("price", hold+"");
                setResult(RESULT_OK,returnIntent);
                AddExpense.this.finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getBaseContext(), "Credit Card activity crashed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getBaseContext(), "Successfully added credit card: " + data.getStringExtra("Cardnumber"), Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            this.finish();
        }
    }
    private void submitClick(){
        Intent creditIntent = new Intent(AddExpense.this, CreditCardActivity.class);
        startActivityForResult(creditIntent, Activity.RESULT_OK);
    }
    private void isReadyToPay() {
        IsReadyToPayRequest request = IsReadyToPayRequest.newBuilder().addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD).addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD).build();
        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(
                new OnCompleteListener<Boolean>() {
                    public void onComplete(Task<Boolean> task) {
                        try {
                            boolean result = task.getResult(ApiException.class);
                            if (result == true) {
                                mSubmitExpense.setVisibility(View.VISIBLE);
                            } else {
                                mSubmitExpense.setVisibility(View.GONE);
                            }
                        } catch (ApiException exception) {
                        }
                    }
                });
    }
    private PaymentDataRequest createPaymentDataRequest() {
        PaymentDataRequest.Builder request =
                PaymentDataRequest.newBuilder()
                        .setTransactionInfo(
                                TransactionInfo.newBuilder()
                                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                                        .setTotalPrice("10.00")
                                        .setCurrencyCode("USD")
                                        .build())
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                        .setCardRequirements(
                                CardRequirements.newBuilder()
                                        .addAllowedCardNetworks(Arrays.asList(
                                                WalletConstants.CARD_NETWORK_AMEX,
                                                WalletConstants.CARD_NETWORK_DISCOVER,
                                                WalletConstants.CARD_NETWORK_VISA,
                                                WalletConstants.CARD_NETWORK_MASTERCARD))
                                        .build());

        request.setPaymentMethodTokenizationParameters(createTokenizationParameters());
        return request.build();
    }
    private PaymentMethodTokenizationParameters createTokenizationParameters() {
        return PaymentMethodTokenizationParameters.newBuilder()
                .setPaymentMethodTokenizationType(WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_PAYMENT_GATEWAY)
                .addParameter("gateway", "stripe")
                .addParameter("stripe:publishableKey", "pk_test_6pRNASCoBOKtIshFeQd4XMUh")
                .addParameter("stripe:version", "5.1.0")
                .build();
    }
}
