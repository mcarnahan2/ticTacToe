package edu.apsu.csci;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class NumberScrabbleActivity extends AppCompatActivity {
    //creates the variables to be used
    private HashMap<Integer, Integer> tttMap;
    private ArrayList<Integer> numbersUsed;
    private int count = 0;
    private int[] ids = {
            R.id.editText00, R.id.editText01, R.id.editText02,
            R.id.editText10, R.id.editText11, R.id.editText12,
            R.id.editText20, R.id.editText21, R.id.editText22
    };
    private TextView[][] textViews = new TextView[3][3];
    AlertDialog.Builder builder;
    private Integer[][] position = new Integer[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_scrabble);

        //creates the hash map and adds values into the hashmap
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

        //initializes the array textViews
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String tvID = "textView" + i + j;
                int id = getResources().getIdentifier(tvID, "id", getPackageName());
                textViews[i][j] = findViewById(id);
            }
        }

        findViewById(R.id.submit_button).setOnClickListener(new SubmitListener());
        findViewById(R.id.rules_button).setOnClickListener(new RulesListener());
        findViewById(R.id.reset_button).setOnClickListener(new ResetListener());
        findViewById(R.id.quit_button).setOnClickListener(new QuitListener());

        //sets the initial color of textView to red
        TextView tv = findViewById(R.id.textView);
        tv.setTextColor(Color.RED);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class SubmitListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            TextView textView = findViewById(R.id.textView);
            EditText editText;
            String etStr;

            //starts a for loop to get all of the ids from the array ids
            for (int id : ids) {

                //checks if the hashmap contains the id
                if (tttMap.containsKey(id)) {

                    editText = findViewById(id);
                    etStr = editText.getText().toString();

                    //checks to see if etStr has anything in it
                    if (etStr.trim().length() > 0) {
                        int etNum = 0;

                        //uses a try catch to change etStr to an integer
                        try {
                            etNum = Integer.parseInt(etStr);
                        } catch (NumberFormatException ex) {
                            Log.i("IDS", "Invalid integer format");
                        }

                        //gets the textview from the hashmap using the key id
                        int tvId = tttMap.get(id);
                        TextView tv = findViewById(tvId);

                        //checks if the number entered is in the range 1-9 and if not shows a toast message
                        if (etNum >= 1 && etNum <= 9) {

                            //checks if the array list numbersUsed is empty to determine if this is the first time
                            if (numbersUsed.isEmpty()) {
                                tv.setText(etStr);
                                tv.setVisibility(View.VISIBLE);
                                tv.setTextColor(Color.RED);
                                editText.setVisibility(View.INVISIBLE);
                                textView.setText("Player 2's Turn");
                                textView.setTextColor(Color.BLUE);
                                numbersUsed.add(etNum);

                            } else {
                                //checks for repeat numbers in the array list and only checks it if the editText is visible
                                if (numbersUsed.contains(etNum) && editText.isShown()) {
                                    Toast.makeText(getApplicationContext(), "Please enter a number that has not been entered already", Toast.LENGTH_LONG).show();
                                }
                                //if the number is not used it sets everything
                                else if (!numbersUsed.contains(etNum)) {
                                    tv.setText(etStr);
                                    tv.setVisibility(View.VISIBLE);
                                    editText.setVisibility(View.INVISIBLE);
                                    numbersUsed.add(etNum);

                                    //checks to see whose turn it is
                                    if (count % 2 == 0) {
                                        textView.setText("Player 1's Turn");
                                        textView.setTextColor(Color.RED);
                                        tv.setTextColor(Color.BLUE);
                                        count += 1;
                                    } else {
                                        textView.setText("Player 2's Turn");
                                        textView.setTextColor(Color.BLUE);
                                        tv.setTextColor(Color.RED);
                                        count += 1;
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Number must be between 1 and 9", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            //checks if there is a winner
            if (winner()) {
                //checks which player had the last move
                if (count % 2 == 0) {
                    textView.setText("Player 1 is the Winner!!!!");
                    textView.setTextColor(Color.RED);
                } else {
                    textView.setText("Player 2 is the Winner!!!!");
                    textView.setTextColor(Color.BLUE);
                }

                //resets the screen
                numbersUsed.clear();
                for (int id : ids) {
                    if (tttMap.containsKey(id)) {
                        editText = findViewById(id);
                        editText.setText("");
                        editText.setVisibility(View.VISIBLE);
                        int tvId = tttMap.get(id);
                        TextView tv = findViewById(tvId);
                        tv.setText("0");
                        tv.setVisibility(View.INVISIBLE);
                    }

                }

            }

            if (count == 8) {
                textView.setText("Game Tied");
                //resets the screen
                numbersUsed.clear();
                for (int id : ids) {
                    if (tttMap.containsKey(id)) {
                        editText = findViewById(id);
                        editText.setText("");
                        editText.setVisibility(View.VISIBLE);
                        int tvId = tttMap.get(id);
                        TextView tv = findViewById(tvId);
                        tv.setText("0");
                        tv.setVisibility(View.INVISIBLE);
                        count=0;
                    }

                }
            }

        }
    }

    class QuitListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    class RulesListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            alertDialog();
        }
    }

    //creates an alert dialog
    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Players will take turns choosing a number from 1 to 9 without repeating numbers.  If the player chooses a number and completes a row, and the row adds up to 15, that player wins!  To play choose a square and type in a number.  Click submit and your move will be recorded.");
        dialog.setTitle("Rules");
        dialog.setPositiveButton("Let's Play",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Have fun!", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    class ResetListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            EditText et;
            TextView textView = findViewById(R.id.textView);
            textView.setText("Player 1's Turn");
            textView.setTextColor(Color.RED);
            numbersUsed.clear();
            for (int id : ids) {
                if (tttMap.containsKey(id)) {
                    et = findViewById(id);
                    et.setText("");
                    et.setVisibility(View.VISIBLE);
                    int tvId = tttMap.get(id);
                    TextView tv = findViewById(tvId);
                    tv.setText("0");
                    tv.setVisibility(View.INVISIBLE);
                }

            }
        }
    }

    private boolean winner() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    position[i][j] = Integer.parseInt(textViews[i][j].getText().toString());
                } catch (NumberFormatException ex) {
                    Log.i("IDS", "Invalid integer format");
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            if (position[i][0] + position[i][1] + position[i][2] == 15 && position[i][0] != 0 && position[i][1] != 0 && position[i][2] != 0) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (position[0][i] + position[1][i] + position[2][i] == 15 && position[0][i] != 0 && position[1][i] != 0 && position[2][i] != 0) {
                return true;
            }
        }

        if (position[0][0] + position[1][1] + position[2][2] == 15 && position[0][0] != 0 && position[1][1] != 0 && position[2][2] != 0) {
            return true;
        }

        if (position[2][0] + position[1][1] + position[0][2] == 15 && position[2][0] != 0 && position[1][1] != 0 && position[0][2] != 0) {
            return true;
        }


        return false;
    }
}
