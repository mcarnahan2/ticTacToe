package edu.apsu.csci;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RandomTurnActivity extends AppCompatActivity implements View.OnClickListener {

    //Used a 2D ARRAY for the buttons
    private Button[][] buttons = new Button[3][3];

    private Random random;

    private TextView textview_player;
    private int player1 = 1;
    private int player2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_turn);

        textview_player = findViewById(R.id.textview_player);
        textview_player.setText("Player's Turn: " + player1);

        //Used to get all button ids stored into the array
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                String buttonID = "button_" + i + k;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][k] = findViewById(resID);
                buttons[i][k].setOnClickListener(this);
            }
        }


        Button buttonReset = findViewById(R.id.reset_button);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });

        Button buttonQuit = findViewById(R.id.quit_button);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /* Starts the onClick for the game tmp is set to be random, choosing who will go next.
    Then the winner is chosn by the class Winner() which uses for loops to determine if all X's or O's
    have been used in a row, diagonally, vertical, and horizontal.
     */
    @Override
    public void onClick(View v) {
        random = new Random();
        int tmp = (int) (Math.random() * 2 + 1);

        textview_player.setText("Player's Turn: " + tmp);

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1 == 1 && tmp == 1) {
            ((Button) v).setText("X");
        }
        if (player2 == 2 && tmp == 2) {
            ((Button) v).setText("O");
        }

        if (Winner()) {
            if (tmp == 1) {
                player1Winner();
            } else if (tmp == 2) {
                player2Winner();
            } else {
            Tie();
             }
        }

    }

    /* For loops to find the if X's and O's have been used three times in a row.
    String position is equal to the buttons text (X or O) and gets the location of where the button
    is, example:
    position[0][0] = buttons[0][0]
     */
    private boolean Winner() {
        String[][] position = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                position[i][k] = buttons[i][k].getText().toString();
            }
        }
        //vertical
        for (int i = 0; i < 3; i++) {
            if (position[i][0].equals(position[i][1])
                    && position[i][0].equals(position[i][2])
                    && !position[i][0].equals("")) {
                return true;
            }
        }
        //horizontal
        for (int i = 0; i < 3; i++) {
            if (position[0][i].equals(position[1][i])
                    && position[0][i].equals(position[2][i])
                    && !position[0][i].equals("")) {
                return true;
            }
        }

        //diagonally
        if (position[0][0].equals(position[1][1])
                && position[0][0].equals(position[2][2])
                && !position[0][0].equals("")) {
            return true;
        }
        //diagonally
        if (position[0][2].equals(position[1][1])
                && position[0][2].equals(position[2][0])
                && !position[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Winner() {
        Toast.makeText(this, "Winner is: Player 1! GAME OVER!", Toast.LENGTH_LONG).show();
        GameOver();
    }

    private void player2Winner() {
        Toast.makeText(this, "Winner is: Player 2! GAME OVER!", Toast.LENGTH_LONG).show();
        GameOver();
    }

    private void Tie() {
        String[][] position = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                position[i][k] = buttons[i][k].getText().toString();
                if (position[i][k].equals("")) {
                    Toast.makeText(this, "It's a TIE! GAME OVER!", Toast.LENGTH_LONG).show();
                    GameOver();
                }
            }
        }

    }
    /*Both Reset() and GameOver() do the same thing setting all buttons strings to "" and setting
    player's turn back to a random integer. Reest() is for a button.
     */
    private void Reset() {
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                buttons[i][k].setText("");
                textview_player.setText("Player's Turn: " + player1);
            }
        }
    }

    private void GameOver() {
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                buttons[i][k].setText("");
                textview_player.setText("Player's Turn: " + player1);
            }
        }
    }

}


