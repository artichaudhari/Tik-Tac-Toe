package com.example.tiktactoegame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;
    int activePlayer = 0; // 0 - X, 1 - O
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; // 2 - Null
    int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6} // Diagonals
    };

    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        if (!gameActive) {
            gameReset(view);
            return;
        }

        if (gameState[tappedImage] == 2) {
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);

            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                updateStatus("O's Turn - Tap to play");
            } else {
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                updateStatus("X's Turn - Tap to play");
            }

            img.animate().translationYBy(1000f).setDuration(300);
            checkForWinner();
        }
    }

    private void checkForWinner() {
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                // Somebody has won! - Find out who!
                String winnerStr = (gameState[winPosition[0]] == 0) ? "X has won" : "O has won";
                gameActive = false;
                updateStatus(winnerStr);
                return;
            }
        }

        // Check for draw
        boolean draw = true;
        for (int state : gameState) {
            if (state == 2) {
                draw = false;
                break;
            }
        }

        if (draw) {
            gameActive = false;
            updateStatus("It's a Draw");
        }
    }

    private void updateStatus(String statusText) {
        TextView status = findViewById(R.id.status);
        status.setText(statusText);
    }

    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        int[] imageIds = {
                R.id.imageView0, R.id.imageView1, R.id.imageView2,
                R.id.imageView3, R.id.imageView4, R.id.imageView5,
                R.id.imageView6, R.id.imageView7, R.id.imageView8
        };
        for (int id : imageIds) {
            ((ImageView) findViewById(id)).setImageResource(0);
        }
        updateStatus("X's Turn - Tap to play");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
