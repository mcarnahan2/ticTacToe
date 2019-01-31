package edu.apsu.csci;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

public class NumberScrabbleActivity extends AppCompatActivity {
    private HashMap<Integer, Integer> tttMap;
    private int [] ids = {
      R.id.editText00, R.id.editText01, R.id.editText02,
      R.id.editText10, R.id.editText11, R.id.editText12,
      R.id.editText20, R.id.editText21, R.id.editText22
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_scrabble);

        EditText editText;

        for(int id : ids) {
            editText = findViewById(id);
        }

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
