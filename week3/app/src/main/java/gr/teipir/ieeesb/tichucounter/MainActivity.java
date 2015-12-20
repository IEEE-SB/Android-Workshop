package gr.teipir.ieeesb.tichucounter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    /* Variables for storing total scores of GAME */
    int scoreTeamA = 0;
    int scoreTeamB = 0;

    /* Variables for storing total scores per ROUND */
    int currentScoreTeamA = 0;
    int currentScoreTeamB = 0;

    /* Variables for storing scores from cards per ROUND, not for grand-tichu, tichu, one-two */
    int currentCardsScoreTeamA = 0;
    int currentCardsScoreTeamB = 100;

    /* Flags for grandTichu to calculate prizeA, prizeB in
     * function updateScores(); -1 Lost, 0 Does not Apply, 1 Won */
    int grandTichuTeamA = 0;
    int grandTichuTeamB = 0;

    /* Flags for tichu to calculate prizeA, prizeB in
     * function updateScores(); -1 Lost, 0 Does not Apply, 1 Won */
    int tichuTeamA = 0;
    int tichuTeamB = 0;

    /* Flags for one-two to calculate prizeA, prizeB in
     * function updateScores(); -1 Lost, 0 Does not Apply, 1 Won */
    boolean oneTwoTeamA = false;
    boolean oneTwoTeamB = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            new_round();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("scoreTeamA", scoreTeamA);
        savedInstanceState.putInt("scoreTeamB", scoreTeamB);

        TextView historyTeamA = (TextView) findViewById(R.id.historyTeamA);
        TextView historyTeamB = (TextView) findViewById(R.id.historyTeamB);

        savedInstanceState.putString("historyTeamA", String.valueOf(historyTeamA.getText() ) );
        savedInstanceState.putString("historyTeamB", String.valueOf(historyTeamB.getText() ) );
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        scoreTeamA = savedInstanceState.getInt("scoreTeamA");
        scoreTeamB = savedInstanceState.getInt("scoreTeamB");

        String historyTeamA = savedInstanceState.getString("historyTeamA");
        String historyTeamB = savedInstanceState.getString("historyTeamB");

        update_after_restore(historyTeamA, historyTeamB);
    }

    private void update_after_restore(String histTeamA, String histTeamB) {

        TextView teamAscore = (TextView) findViewById(R.id.scoreTeamA);
        TextView teamBscore = (TextView) findViewById(R.id.scoreTeamB);

        teamAscore.setText(String.valueOf(scoreTeamA));
        teamBscore.setText(String.valueOf(scoreTeamB));

        TextView historyTeamA = (TextView) findViewById(R.id.historyTeamA);
        TextView historyTeamB = (TextView) findViewById(R.id.historyTeamB);

        historyTeamA.setText(histTeamA);
        historyTeamB.setText(histTeamB);

    }


    /* Adds 50 points on Team A, and removes 50 points from Team B */
    public void plus50_pressed(View view) {
        if (currentCardsScoreTeamA + 50 <= 100 && !(oneTwoTeamA || oneTwoTeamB)) {
            currentCardsScoreTeamA += 50;
            currentCardsScoreTeamB = 100 - currentCardsScoreTeamA;
        }
        updateScores();
    }

    /* Adds 20 points on Team A, and removes 20 points from Team B */
    public void plus20_pressed(View view) {
        if (currentCardsScoreTeamA + 20 <= 100 && !(oneTwoTeamA || oneTwoTeamB)) {
            currentCardsScoreTeamA += 20;
            currentCardsScoreTeamB = 100 - currentCardsScoreTeamA;
        }
        updateScores();
    }

    /* Adds 10 points on Team A, and removes 10 points from Team B */
    public void plus10_pressed(View view) {
        if (currentCardsScoreTeamA + 10 <= 100 && !(oneTwoTeamA || oneTwoTeamB)) {
            currentCardsScoreTeamA += 10;
            currentCardsScoreTeamB = 100 - currentCardsScoreTeamA;
        }
        updateScores();
    }

    /* Adds 5 points on Team A, and removes 5 points from Team B */
    public void plus5_pressed(View view) {
        if (currentCardsScoreTeamA + 5 <= 100 && !(oneTwoTeamA || oneTwoTeamB)) {
            currentCardsScoreTeamA += 5;
            currentCardsScoreTeamB = 100 - currentCardsScoreTeamA;
        }
        updateScores();
    }

    /* Adds current round's score to the Total Score */
    public void go_pressed(View view) {

        TextView teamAscore = (TextView) findViewById(R.id.scoreTeamA);
        TextView teamBscore = (TextView) findViewById(R.id.scoreTeamB);

        scoreTeamA += currentScoreTeamA;
        scoreTeamB += currentScoreTeamB;

        teamAscore.setText(String.valueOf(scoreTeamA));
        teamBscore.setText(String.valueOf(scoreTeamB));

        update_history();

        new_round();

    }

    /* Depending on the flags' values, round's points are being calculated */
    private void updateScores() {

        int prizeA = 0;
        int prizeB = 0;

        currentScoreTeamA = 0;
        currentScoreTeamB = 0;

        if (tichuTeamA == 1) {
            prizeA += 100;
        } else if (tichuTeamA == -1) {
            prizeA += -100;
        }

        if (grandTichuTeamA == 1) {
            prizeA += 200;
        } else if (grandTichuTeamA == -1) {
            prizeA += -200;
        }

        if (tichuTeamB == 1) {
            prizeB += 100;
        } else if (tichuTeamB == -1) {
            prizeB += -100;
        }

        if (grandTichuTeamB == 1) {
            prizeB += 200;
        } else if (grandTichuTeamB == -1) {
            prizeB += -200;
        }

        if (oneTwoTeamA) {
            prizeA += 200;
        } else if (oneTwoTeamB) {
            prizeB += 200;
        }

        if (oneTwoTeamA || oneTwoTeamB) {
            currentScoreTeamA = prizeA;
            currentScoreTeamB = prizeB;
        }

        if (!oneTwoTeamA && !oneTwoTeamB) {
            currentScoreTeamA = prizeA + currentCardsScoreTeamA;
            currentScoreTeamB = prizeB + currentCardsScoreTeamB;
        }

        TextView teamA = (TextView) findViewById(R.id.currentPointsTeamA);
        TextView teamB = (TextView) findViewById(R.id.currentPointsTeamB);

        teamA.setText(getString(R.string.currentPointsTeamA) + String.valueOf(currentScoreTeamA));
        teamB.setText(getString(R.string.currentPointsTeamB) + String.valueOf(currentScoreTeamB));

    }

    /* Starts a new round */
    private void new_round() {

        reset_vars();

        TextView initScoreTeamA = (TextView) findViewById(R.id.currentPointsTeamA);
        initScoreTeamA.setText(getString(R.string.currentPointsTeamA) + String.valueOf(currentScoreTeamA));

        TextView initScoreTeamB = (TextView) findViewById(R.id.currentPointsTeamB);
        initScoreTeamB.setText(getString(R.string.currentPointsTeamB) + String.valueOf(currentScoreTeamB));
    }

    /* Reset all necessary variables, TextViews and Buttons*/
    private void reset_vars() {

        currentScoreTeamA = 0;
        currentScoreTeamB = 0;
        currentCardsScoreTeamA = 0;
        currentCardsScoreTeamB = 100;
        grandTichuTeamA = 0;
        grandTichuTeamB = 0;
        tichuTeamA = 0;
        tichuTeamB = 0;
        oneTwoTeamA = false;
        oneTwoTeamB = false;

        tichuTeamA_deactivate();
        tichuTeamB_deactivate();
        grandTichuTeamA_deactivate();
        grandTichuTeamB_deactivate();
        oneTwoTeamA_deactivate();
        oneTwoTeamB_deactivate();

    }

    /* Starts a new round after CLR button being pressed */
    public void clear(View view) {

        new_round();

    }

    /* Adds current round's score to history TextView */
    private void update_history() {

        TextView teamAhistory = (TextView) findViewById(R.id.historyTeamA);
        TextView teamBhistory = (TextView) findViewById(R.id.historyTeamB);

        teamAhistory.setText(teamAhistory.getText() + "\n" + currentScoreTeamA);
        teamBhistory.setText(teamBhistory.getText() + "\n" + currentScoreTeamB);

    }

    /* Toggles oneTwoTeamA flag and deactivates Tichu and GrandTichu from Team B */
    public void oneTwoTeamA_pressed(View view) {
        oneTwoTeamB_deactivate();

        oneTwoTeamA = !oneTwoTeamA;

        if ( tichuTeamB == 1 ) {
            tichuTeamB_deactivate();
        }
        if (grandTichuTeamB == 1) {
            grandTichuTeamB_deactivate();
        }

        updateScores();

    }

    /* Toggles oneTwoTeamB flag and deactivates Tichu and GrandTichu from Team A */
    public void oneTwoTeamB_pressed(View view) {
        oneTwoTeamA_deactivate();

        oneTwoTeamB = !oneTwoTeamB;

        if ( tichuTeamA == 1 ) {
            tichuTeamA_deactivate();
        }
        if (grandTichuTeamA == 1) {
            grandTichuTeamA_deactivate();
        }

        updateScores();

    }

    /* Resets one-two button and flag for Team A */
    private void oneTwoTeamA_deactivate() {

        ToggleButton btn = (ToggleButton) findViewById(R.id.oneTwoTeamA);
        btn.setChecked(false);
        oneTwoTeamA = false;

    }

    /* Resets one-two button and flag for Team B */
    private void oneTwoTeamB_deactivate() {

        ToggleButton btn = (ToggleButton) findViewById(R.id.oneTwoTeamB);
        btn.setChecked(false);
        oneTwoTeamB = false;

    }


    /* Depending on tichuTeamA value, appropriate actions are done.
     * tichuTeamA is -1 for a lost Tichu, 1 for a won Tichu and 0 if it wasn't called */
    public void tichuTeamA_pressed(View view) {

        ToggleButton teamA_tichu = (ToggleButton) findViewById(R.id.tichuTeamA);

        if (tichuTeamA == 0) {

            tichuTeamA = 1;
            teamA_tichu.setChecked(true);
            teamA_tichu.setTextColor(Color.GREEN);

            if ( tichuTeamB == 1 ) {
                tichuTeamB_deactivate();
            }
            if ( grandTichuTeamA == 1 ) {
                grandTichuTeamA_deactivate();
            }
            if (grandTichuTeamB == 1) {
                grandTichuTeamB_deactivate();
            }
            if ( oneTwoTeamB ) {
                oneTwoTeamB_deactivate();
            }

        } else if (tichuTeamA == 1) {

            tichuTeamA = -1;
            teamA_tichu.setChecked(true);
            teamA_tichu.setTextColor(Color.RED);

        } else if (tichuTeamA == -1) {

            tichuTeamA = 0;
            teamA_tichu.setChecked(false);
            teamA_tichu.setTextColor(Color.BLACK);

        }

        updateScores();

    }

    /* Depending on tichuTeamB value, appropriate actions are done.
     * tichuTeamB is -1 for a lost Tichu, 1 for a won Tichu and 0 if it wasn't called */
    public void tichuTeamB_pressed(View view) {

        ToggleButton teamB_tichu = (ToggleButton) findViewById(R.id.tichuTeamB);

        if (tichuTeamB == 0) {

            tichuTeamB = 1;
            teamB_tichu.setChecked(true);
            teamB_tichu.setTextColor(Color.GREEN);

            if ( tichuTeamA == 1 ) {
                tichuTeamA_deactivate();
            }
            if ( grandTichuTeamA == 1 ) {
                grandTichuTeamA_deactivate();
            }
            if (grandTichuTeamB == 1) {
                grandTichuTeamB_deactivate();
            }
            if ( oneTwoTeamA ) {
                oneTwoTeamA_deactivate();
            }

        } else if (tichuTeamB == 1) {

            tichuTeamB = -1;
            teamB_tichu.setChecked(true);
            teamB_tichu.setTextColor(Color.RED);

        } else if (tichuTeamB == -1) {

            tichuTeamB = 0;
            teamB_tichu.setChecked(false);
            teamB_tichu.setTextColor(Color.BLACK);

        }

        updateScores();

    }

    /* Deactivates Tichu for Team A */
    private void tichuTeamA_deactivate() {

        ToggleButton teamA_tichu = (ToggleButton) findViewById(R.id.tichuTeamA);

        tichuTeamA = 0;
        teamA_tichu.setChecked(false);
        teamA_tichu.setTextColor(Color.BLACK);

        updateScores();

    }

    /* Deactivates Tichu for Team B */
    private void tichuTeamB_deactivate() {

        ToggleButton teamB_tichu = (ToggleButton) findViewById(R.id.tichuTeamB);

        tichuTeamB = 0;
        teamB_tichu.setChecked(false);
        teamB_tichu.setTextColor(Color.BLACK);

        updateScores();
    }


    /* Depending on grandTichuTeamA value, appropriate actions are done.
     * grandTichuTeamA is -1 for a lost Grand Tichu, 1 for a won Grand Tichu and 0 if it wasn't called */
    public void grandTichuTeamA_pressed(View view) {

        ToggleButton teamA_grandTichu = (ToggleButton) findViewById(R.id.grandTichuTeamA);

        if (grandTichuTeamA == 0) {

            grandTichuTeamA = 1;
            teamA_grandTichu.setChecked(true);
            teamA_grandTichu.setTextColor(Color.GREEN);

            if ( tichuTeamA == 1 ) {
                tichuTeamA_deactivate();
            }
            if ( tichuTeamB == 1 ) {
                tichuTeamB_deactivate();
            }
            if (grandTichuTeamB == 1) {
                grandTichuTeamB_deactivate();
            }
            if ( oneTwoTeamB ) {
                oneTwoTeamB_deactivate();
            }

        } else if (grandTichuTeamA == 1) {

            grandTichuTeamA = -1;
            teamA_grandTichu.setChecked(true);
            teamA_grandTichu.setTextColor(Color.RED);

        } else if (grandTichuTeamA == -1) {

            grandTichuTeamA = 0;
            teamA_grandTichu.setChecked(false);
            teamA_grandTichu.setTextColor(Color.BLACK);

        }

        updateScores();

    }

    /* Depending on grandTichuTeamB value, appropriate actions are done.
     * grandTichuTeamB is -1 for a lost Grand Tichu, 1 for a won Grand Tichu and 0 if it wasn't called */
    public void grandTichuTeamB_pressed(View view) {

        ToggleButton teamA_grandTichu = (ToggleButton) findViewById(R.id.grandTichuTeamA);
        ToggleButton teamB_grandTichu = (ToggleButton) findViewById(R.id.grandTichuTeamB);

        if (grandTichuTeamB == 0) {

            grandTichuTeamB = 1;
            teamB_grandTichu.setChecked(true);
            teamB_grandTichu.setTextColor(Color.GREEN);

            if ( tichuTeamA == 1 ) {
                tichuTeamA_deactivate();
            }
            if ( tichuTeamB == 1 ) {
                tichuTeamB_deactivate();
            }
            if (grandTichuTeamA == 1) {
                grandTichuTeamA_deactivate();
            }
            if ( oneTwoTeamA ) {
                oneTwoTeamA_deactivate();
            }

        } else if (grandTichuTeamB == 1) {

            grandTichuTeamB = -1;
            teamB_grandTichu.setChecked(true);
            teamB_grandTichu.setTextColor(Color.RED);

        } else if (grandTichuTeamB == -1) {

            grandTichuTeamB = 0;
            teamB_grandTichu.setChecked(false);
            teamB_grandTichu.setTextColor(Color.BLACK);

        }

        updateScores();
    }

    /* Deactivates Grand Tichu for Team A */
    private void grandTichuTeamA_deactivate() {

        ToggleButton teamA_grandTichu = (ToggleButton) findViewById(R.id.grandTichuTeamA);

        grandTichuTeamA = 0;
        teamA_grandTichu.setChecked(false);
        teamA_grandTichu.setTextColor(Color.BLACK);

        updateScores();

    }

    /* Deactivates Grand Tichu for Team A */
    private void grandTichuTeamB_deactivate() {

        ToggleButton teamB_grandTichu = (ToggleButton) findViewById(R.id.grandTichuTeamB);

        grandTichuTeamB = 0;
        teamB_grandTichu.setChecked(false);
        teamB_grandTichu.setTextColor(Color.BLACK);

        updateScores();

    }

}