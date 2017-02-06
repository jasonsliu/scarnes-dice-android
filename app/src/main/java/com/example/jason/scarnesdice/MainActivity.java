package com.example.jason.scarnesdice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static int p1TotalScore = 0;
    public static int p1RoundScore = 0;
    public static int p2TotalScore = 0;
    public static int p2RoundScore = 0;
    Random dice = new Random();
    Handler timeHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            int rollVal = rollDie();
            if(rollVal != 1) {
                p2RoundScore += rollVal;
                updateScores();
            } else {
                p2RoundScore = 0;
                endComputerTurn();
                return;
            }

            // stop if round score is at least 20
            if(p2RoundScore >= 20) {
                endComputerTurn();
                return;
            }
            timeHandler.postDelayed(this, 500);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void rollButtonPress(View view) {
        int val = rollDie();
        if(val != 1) {
            p1RoundScore += val;
            updateScores();
        } else {
            p1RoundScore = 0;
            computerTurn();
        }
    }

    /**
     * Roll the die and update the die image on the screen
     * @return the die roll value
     */
    private int rollDie() {
        ImageView diceView = (ImageView)findViewById(R.id.diceImage);
        int rollVal = dice.nextInt(6) + 1;
        switch(rollVal) { // roll dice
            case 1:
                diceView.setImageResource(R.drawable.dice1);
                break;
            case 2:
                diceView.setImageResource(R.drawable.dice2);
                break;
            case 3:
                diceView.setImageResource(R.drawable.dice3);
                break;
            case 4:
                diceView.setImageResource(R.drawable.dice4);
                break;
            case 5:
                diceView.setImageResource(R.drawable.dice5);
                break;
            case 6:
                diceView.setImageResource(R.drawable.dice6);
                break;
        }
        return rollVal;
    }

    public void holdButtonPress(View view) {
        p1TotalScore += p1RoundScore;
        p1RoundScore = 0;
        updateScores();
        if(p1TotalScore >= 100) {
            endGame();
            return;
        }
        computerTurn();
    }

    public void resetButtonPress(View view) {
        p1TotalScore = 0;
        p1RoundScore = 0;
        p2TotalScore = 0;
        p2RoundScore = 0;
        updateScores();
        TextView turn = (TextView)findViewById(R.id.turn);
        String who = "Player 1 Turn!";
        turn.setText(who);
        setButtons(true);
    }

    protected void updateScores() {
        TextView p1score = (TextView)findViewById(R.id.p1score);
        String setToMe = "Your score: " + p1TotalScore;
        p1score.setText(setToMe);
        TextView p2score = (TextView)findViewById(R.id.p2score);
        setToMe = "Computer score: " + p2TotalScore;
        p2score.setText(setToMe);
        TextView roundScore = (TextView)findViewById(R.id.roundScore);
        setToMe = "Round score: " + p1RoundScore;
        roundScore.setText(setToMe);
    }

    protected void computerTurn() {
        TextView turn = (TextView)findViewById(R.id.turn);
        String who = "Computer's turn!";
        turn.setText(who);

        // toggle buttons
        setButtons(false);

        // computer plays
        timeHandler.postDelayed(timerRunnable, 500);
    }

    protected void endComputerTurn(){
        p2TotalScore += p2RoundScore;
        p2RoundScore = 0;
        updateScores();

        if(p2TotalScore >= 100) {
            endGame();
            return;
        }

        // toggle buttons
        setButtons(true);

        TextView turn = (TextView)findViewById(R.id.turn);
        String who = "Player 1 Turn!";
        turn.setText(who);
    }

    private void setButtons(Boolean set) {
        Button b_roll = (Button)findViewById(R.id.rollButton);
        Button b_hold = (Button)findViewById(R.id.holdButton);
        b_roll.setEnabled(set);
        b_hold.setEnabled(set);
    }

    private void endGame() {
        updateScores();
        TextView turn = (TextView)findViewById(R.id.turn);
        String who;
        if(p1TotalScore >= 100) {
            who = "YOU WON! :)";
        } else {
            who = "Computer won :O";
        }
        turn.setText(who);
        setButtons(false);
    }
}
