package edu.apsu.csci;

import android.graphics.Color;
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
    private int count=0;
    private int [] ids = {
        R.id.editText00, R.id.editText01, R.id.editText02,
        R.id.editText10, R.id.editText11, R.id.editText12,
        R.id.editText20, R.id.editText21, R.id.editText22
    };
    private TextView[][] textViews = new TextView[3][3];

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

        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String tvID = "textView" + i + j;
                int id = getResources().getIdentifier(tvID, "id", getPackageName());
                textViews[i][j] = findViewById(id);
            }
        }

        findViewById(R.id.submit_button).setOnClickListener(new SubmitListener());
    }

    class SubmitListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            TextView textView = findViewById(R.id.textView);
            EditText editText;
            String etStr;

            for(int id : ids){
                if(tttMap.containsKey(id)) {
                    editText = findViewById(id);

                    etStr = editText.getText().toString();


                    if(etStr.trim().length()>0){
                        int etNum = 0;
                        try{
                            etNum = Integer.parseInt(etStr);
                        } catch(NumberFormatException ex){
                            Log.i("IDS", "Invalid integer format");
                        }

                        int tvId = tttMap.get(id);
                        TextView tv = findViewById(tvId);

                        if(etNum>=1 && etNum<=9){
                            if(numbersUsed.isEmpty()){
                                Log.i("WHERE", "I am in the isEmpty statement before, count is " + count);
                                tv.setText(etStr);
                                tv.setVisibility(View.VISIBLE);
                                editText.setVisibility(View.INVISIBLE);
                                Log.i("WHERE", "I am in the isEmpty statement, count is " + count);
                                textView.setText("Player 2's Turn");
                                numbersUsed.add(etNum);

                            } else {
                                if(numbersUsed.contains(etNum)  && editText.isShown()) {
                                    Toast.makeText(getApplicationContext(), "Please enter a number that has not been entered already", Toast.LENGTH_SHORT).show();
                                } else if(!numbersUsed.contains(etNum)){
                                    tv.setText(etStr);
                                    tv.setVisibility(View.VISIBLE);
                                    editText.setVisibility(View.INVISIBLE);
                                    numbersUsed.add(etNum);

                                    if(count % 2 == 0){
                                        textView.setText("Player 1's Turn");
                                        count+=1;
                                    } else {
                                        textView.setText("Player 2's Turn");
                                        count+=1;
                                    }
                                }
                            }
                        } else{
                            Toast.makeText(getApplicationContext(), "Not in the range", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            if(winner()){
                Log.i("WHERE", "Winner");
            }

        }
    }

    private boolean winner() {
        Integer[][] position = new Integer[3][3];

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                try{
                    position[i][j] = Integer.parseInt(textViews[i][j].getText().toString());
                } catch(NumberFormatException ex){
                    Log.i("IDS", "Invalid integer format");
                }
            }
        }

        for(int i = 0; i < 3; i++){
            if(position[i][0] + position[i][1] + position[i][2] == 15 && position[i][0] != 0 && position[i][1] != 0 && position[i][2] != 0){
                return true;
            }
        }

        for(int i = 0; i < 3; i++){
            if(position[0][i] + position[1][i] + position[2][i] == 15 && position[0][i] != 0 && position[1][i] != 0 && position[2][i] != 0){
                return true;
            }
        }

        if(position[0][0] + position[1][1] + position[2][2] == 15 && position[0][0] != 0 && position[1][1] != 0 && position[2][2] != 0){
            return true;
        }

        if(position[2][0] + position[1][1] + position[0][2] == 15 && position[2][0] != 0 && position[1][1] != 0 && position[0][2] != 0){
            return true;
        }


        return false;
    }
}
