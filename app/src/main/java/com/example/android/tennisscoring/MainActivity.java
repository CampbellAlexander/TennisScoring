package com.example.android.tennisscoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView player1SetsWon, player1GamesWon, player1GameScore,
            player2SetsWon, player2GamesWon, player2GameScore;

    private Button player1LetButton, player1MissServeButton, player1PointButton,
            player2LetButton, player2MissServeButton, player2PointButton;

    private Game game = Game.newGame();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        refreshUI();
    }

    public void player1Let(View view) {
        toast("Left player let." + score());
    }
    public void player2Let(View view) {
        toast("Right player let." + score());
    }

    public void player1MissServe(View view) {
        game = game.player1MissServe();
        toast(game.player1MissedServes == 1
                ? "Left player missed the first serve!" + score()
                : "Left player missed both serves!" + score()
        );
        refreshUI();
    }
    public void player2MissServe(View view) {
        game = game.player2MissServe();
        toast(game.player2MissedServes == 1
                ? "Right player missed the first serve!" + score()
                : "Right player missed both serves!" + score()
        );
        refreshUI();
    }

    public void player1Point(View view) {
        Game oldGame = game;
        game = game.player1Point();

        if (game.gameOver())
            toast("The match goes to Left player!");
        else if (game.player1SetsWon > oldGame.player1SetsWon)
            toast("The set goes to Left player!" + score());
        else if (oldGame.player1GameScore >= 3 && game.player1GameScore == 0)
            toast("The game goes to Left player!" + score());
        else if (game.leftHasAdvantage())
            toast("Advantage left player!" + score());
        else if (game.deuce())
            toast("Deuce!" + score());
        else
            toast("Point goes to Left player!" + score());

        refreshUI();
    }

    public void player2Point(View view) {
        Game oldGame = game;
        game = game.player2Point();

        if (game.gameOver())
            toast("The match goes to Right player!");
        else if (game.player2SetsWon > oldGame.player2SetsWon)
            toast("The set goes to Right player!" + score());
        else if (oldGame.player2GameScore >= 3 && game.player2GameScore == 0)
            toast("The game goes to Right player!" + score());
        else if (game.rightHasAdvantage())
            toast("Advantage right player!" + score());
        else if (game.deuce())
            toast("Deuce!" + score());
        else
            toast("Point goes to Right player!" + score());

        refreshUI();
    }


    public void reset(View view) {
        game = Game.newGame();
        refreshUI();
        player1PointButton.setEnabled(true);
        player2PointButton.setEnabled(true);
    }


    private String score() {
        return "\n" + game.player1GameScore() + " - " + game.player2GameScore();
    }


    private void refreshUI() {
        refreshScores();
        if (game.gameOver()) disableAllButtons();
        else refreshServeButtons();
    }

    private void refreshScores() {
        player1SetsWon.setText(game.player1SetsWon());
        player1GamesWon.setText(game.player1GamesWon());
        player1GameScore.setText(game.player1GameScore());

        player2SetsWon.setText(game.player2SetsWon());
        player2GamesWon.setText(game.player2GamesWon());
        player2GameScore.setText(game.player2GameScore());
    }

    private void refreshServeButtons() {
        if (game.server == 1) {
            player1LetButton.setEnabled(true);
            player1MissServeButton.setEnabled(true);
            player2LetButton.setEnabled(false);
            player2MissServeButton.setEnabled(false);
        }
        else {
            player2LetButton.setEnabled(true);
            player2MissServeButton.setEnabled(true);
            player1LetButton.setEnabled(false);
            player1MissServeButton.setEnabled(false);
        }
    }

    private void disableAllButtons() {
        player1LetButton.setEnabled(false);
        player1MissServeButton.setEnabled(false);
        player1PointButton.setEnabled(false);

        player2LetButton.setEnabled(false);
        player2MissServeButton.setEnabled(false);
        player2PointButton.setEnabled(false);
    }


    private void initializeViews() {
        player1SetsWon = findViewById(R.id.player1_sets_won);
        player1GamesWon = findViewById(R.id.player1_games_won);
        player1GameScore = findViewById(R.id.player1_game_score);
        player1LetButton = findViewById(R.id.player1_let);
        player1MissServeButton = findViewById(R.id.player1_miss_serve);
        player1PointButton = findViewById(R.id.player1_point);

        player2SetsWon = findViewById(R.id.player2_sets_won);
        player2GamesWon = findViewById(R.id.player2_games_won);
        player2GameScore = findViewById(R.id.player2_game_score);
        player2LetButton = findViewById(R.id.player2_let);
        player2MissServeButton = findViewById(R.id.player2_miss_serve);
        player2PointButton = findViewById(R.id.player2_point);
    }


    private void toast(String announcement) {
        Toast toast = Toast.makeText(this, announcement, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 120);
        LinearLayout layout = (LinearLayout) toast.getView();
        if (layout.getChildCount() > 0) {
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        }
        toast.show();
    }
}
