package edu.apsu.csci;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class NumberScrabbleActivity extends AppCompatActivity {
    private HashMap<Integer, Integer> tttMap;
    private ArrayList<Integer> numbersUsed;
    private int count=2;
    private int [] ids = {
        R.id.editText00, R.id.editText01, R.id.editText02,
        R.id.editText10, R.id.editText11, R.id.editText12,
        R.id.editText20, R.id.editText21, R.id.editText22
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

        numbersUsed = new ArrayList<>();

        findViewById(R.id.submit_button).setOnClickListener(new SubmitListener());
    }

    class SubmitListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            TextView textView = findViewById(R.id.textView);
            if(count % 2 == 0){
                textView.setText("Player 2's Turn");
            } else {
                textView.setText("Player 1's Turn");
            }
            count+=1;
            EditText editText;
            for(int id : ids){
                if(tttMap.containsKey(id)) {
                    editText = findViewById(id);
                    String etStr = editText.getText().toString();

                    Log.i("IDS", "Edit text string is " + etStr);

                    if(!etStr.equals("")) {
                        int etNum = 0;
                        try{
                            etNum = Integer.parseInt(etStr);
                        } catch(NumberFormatException ex){
                            Log.i("NUMBER", "Invalid integer format");
                        }

                        numbersUsed.add(etNum);

                        int tvId = tttMap.get(id);
                        TextView tv = findViewById(tvId);
                        if(etNum>=1 && etNum<=9){
                            for(int num : numbersUsed){
                                
                            }
                            tv.setText(etStr);
                            tv.setVisibility(View.VISIBLE);
                            editText.setVisibility(View.INVISIBLE);
                        } else{
                            Toast.makeText(getApplicationContext(), "Not in the range", Toast.LENGTH_SHORT).show();
                        }

                        String tvStr = tv.getText().toString();
                        Log.i("IDS", "Text view string is " + tvStr);
                    }

                }

            }
        }
    }
}
