package edu.apsu.csci;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class NumberScrabbleActivity extends AppCompatActivity {
    private HashMap<Integer, Integer> tttMap;
    private int [] ids = {
        R.id.editText00, R.id.editText01, R.id.editText02,
        R.id.editText10, R.id.editText11, R.id.editText12,
        R.id.editText20, R.id.editText21, R.id.editText22
    };
    private int [] tvIds = {
            R.id.textView00, R.id.textView01, R.id.textView02,
            R.id.textView10, R.id.textView11, R.id.textView12,
            R.id.textView20, R.id.textView21, R.id.textView22
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_scrabble);

        tttMap = new HashMap<>();
        tttMap.put(R.id.editText00, R.id.textView00);
        tttMap.put(R.id.editText01, R.id.textView01);
        tttMap.put(R.id.editText02, R.id.textView02);
        tttMap.put(R.id.editText10, R.id.textView10);
        tttMap.put(R.id.editText11, R.id.textView11);
        tttMap.put(R.id.editText12, R.id.textView12);
        tttMap.put(R.id.editText20, R.id.textView20);
        tttMap.put(R.id.editText21, R.id.textView21);
        tttMap.put(R.id.editText22, R.id.textView22);

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText;
                for(int id : ids){
                    if(tttMap.containsKey(id)) {
                        editText = findViewById(id);
                        String etStr = editText.getText().toString();

                        Log.i("IDS", "Edit text string is " + etStr);

                        if(!etStr.equals("")) {
                            int tvId = tttMap.get(id);
                            TextView tv = findViewById(tvId);
                            tv.setText(etStr);
                            tv.setVisibility(View.VISIBLE);
                            editText.setVisibility(View.INVISIBLE);
                            String tvStr = tv.getText().toString();
                            Log.i("IDS", "Text view string is " + tvStr);
                        }

                    }

                    /*
                        Works but trying to use hashmap
                        if(!etStr.equals("")){
                        for(int tid : tvIds){
                            tv = findViewById(tid);
                            tv.setText(etStr);
                            String tvStr = tv.getText().toString();
                            Log.i("IDS", "Text view string is " + tvStr);
                        }
                    */

                    }

                }
        });
    }
}
