package com.example.cory.feedthekitty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RemoveExpense extends AppCompatActivity {

    TextView mHeader;
    ListView mExpenseList;
    ArrayList<String> mListItems, mListRemoved;
    ArrayAdapter<String> mAdapter;
    Button mSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_expense);
        mListRemoved = new ArrayList<String>();
        mListItems = (ArrayList<String>) getIntent().getSerializableExtra("list");
        mHeader = (TextView) findViewById(R.id.remove_expense_header);
        mExpenseList = (ListView) findViewById(R.id.remove_expense_list);
        mSubmit = (Button) findViewById(R.id.remove_expense_submit);

        mAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                mListItems);
        mExpenseList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mExpenseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListRemoved.add(mListItems.get(i));
                mListItems.remove(i);
                mAdapter.notifyDataSetChanged();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToCreateEvent();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Log.d("TestActivity", "We going to the main menu");
//        Intent mIntent = new Intent(TestActivity.this, MainMenu.class);
//        mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(mIntent);
        finish();
    }

    private void returnToCreateEvent(){

        Intent returnIntent = new Intent();
        //Toast.makeText(this.getApplicationContext(), mListItems.size()+"", Toast.LENGTH_SHORT).show();
        returnIntent.putExtra("removeList", mListRemoved);
        setResult(RESULT_OK, returnIntent);
        RemoveExpense.this.finish();
    }
}
