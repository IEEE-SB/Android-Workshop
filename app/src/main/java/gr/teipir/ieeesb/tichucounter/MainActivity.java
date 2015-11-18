package gr.teipir.ieeesb.tichucounter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;



public class MainActivity extends AppCompatActivity {

    int scoreTeamA = 0;
    int scoreTeamB = 0;

    int currentScoreTeamA = 0;
    int currentScoreTeamB = 0;

    int currentCardsScoreTeamA = 0;
    int currentCardsScoreTeamB = 0;

    /* -1 Lost, 0 Does not Apply, 1 Won */
    int grandTichuTeamA = 0;
    int grandTichuTeamB = 0;

    /* -1 Lost, 0 Does not Apply, 1 Won */
    int tichuTeamA = 0;
    int tichuTeamB = 0;

    /* 0 Does not Apply, 1 Applies */
    boolean oneTwoTeamA = false;
    boolean oneTwoTeamB = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new_round();
    }

    public void plus50_pressed(View view){
        if (currentCardsScoreTeamA + 50 <= 100 && !(oneTwoTeamA || oneTwoTeamB) ) {
            currentCardsScoreTeamA += 50;
            currentCardsScoreTeamB = 100 - currentCardsScoreTeamA;
        }
        updateScores();
    }

    public void plus20_pressed(View view){
        if (currentCardsScoreTeamA + 20 <= 100 && !(oneTwoTeamA || oneTwoTeamB) ) {
            currentCardsScoreTeamA += 20;
            currentCardsScoreTeamB = 100 - currentCardsScoreTeamA;
        }
        updateScores();
    }

    public void plus10_pressed(View view){
        if (currentCardsScoreTeamA + 10 <= 100 && !(oneTwoTeamA || oneTwoTeamB) ) {
            currentCardsScoreTeamA += 10;
            currentCardsScoreTeamB = 100 - currentCardsScoreTeamA;
        }
        updateScores();
    }

    public void plus5_pressed(View view){
        if (currentCardsScoreTeamA + 5 <= 100 && !(oneTwoTeamA || oneTwoTeamB) ) {
            currentCardsScoreTeamA += 5;
            currentCardsScoreTeamB = 100 - currentCardsScoreTeamA;
        }
        updateScores();
    }


    private void updateScores() {

        int prizeA = 0;
        int prizeB = 0;

        currentScoreTeamA = 0;
        currentScoreTeamB = 0;

        if (tichuTeamA == 1 ){
            prizeA += 100;
        } else if ( tichuTeamA == -1 ){
            prizeA += -100;
        }

        if ( grandTichuTeamA == 1 ){
            prizeA += 200;
        } else if ( grandTichuTeamA == -1 ){
            prizeA += -200;
        }

        if ( tichuTeamB == 1 ) {
            prizeB += 100;
        } else if ( tichuTeamB == -1 ){
            prizeB += -100;
        }

        if ( grandTichuTeamB == 1 ){
            prizeB += 200;
        } else if ( grandTichuTeamB == -1 ){
            prizeB += -200;
        }

        if ( oneTwoTeamA ) {
            prizeA += 200;
        } else if (oneTwoTeamB) {
            prizeB += 200;
        }

        if ( oneTwoTeamA || oneTwoTeamB ) {
            currentScoreTeamA = prizeA;
            currentScoreTeamB = prizeB;
        }

        if ( !oneTwoTeamA  && !oneTwoTeamB ) {
            currentScoreTeamA = prizeA + currentCardsScoreTeamA;
            currentScoreTeamB = prizeB + currentCardsScoreTeamB;
        }

        TextView teamA = (TextView) findViewById(R.id.currentPointsTeamA);
        TextView teamB = (TextView) findViewById(R.id.currentPointsTeamB);

        teamA.setText(getString(R.string.currentPointsTeamA) + String.valueOf(currentScoreTeamA));
        teamB.setText(getString(R.string.currentPointsTeamB) + String.valueOf(currentScoreTeamB));

    }

    private void new_round() {

        reset_vars();

        TextView initScoreTeamA = (TextView) findViewById(R.id.currentPointsTeamA);
        initScoreTeamA.setText(getString(R.string.currentPointsTeamA) + String.valueOf(currentScoreTeamA));

        TextView initScoreTeamB = (TextView) findViewById(R.id.currentPointsTeamB);
        initScoreTeamB.setText(getString(R.string.currentPointsTeamB) + String.valueOf(currentScoreTeamB));
    }

    private void reset_vars() {
        currentScoreTeamA = 0;
        currentScoreTeamB = 0;
        currentCardsScoreTeamA = 0;
        currentCardsScoreTeamB = 0;
        grandTichuTeamA = 0;
        grandTichuTeamB = 0;
        tichuTeamA = 0;
        tichuTeamB = 0;
        oneTwoTeamA = false;
        oneTwoTeamB = false;
    }

    public void clear(View view) {

        new_round();

    }


    public void oneTwoTeamA_pressed(View view) {
        OneTwoTeamB_deactivate();

        oneTwoTeamA = !oneTwoTeamA;

        if ( tichuTeamB == 1 ) {
            tichuTeamB_deactivate();
        }
        if (grandTichuTeamB == 1) {
            grandTichuTeamB_deactivate();
        }

        updateScores();

    }

    public void oneTwoTeamB_pressed(View view) {
        OneTwoTeamA_deactivate();

        oneTwoTeamB = !oneTwoTeamB;

        if ( tichuTeamA == 1 ) {
            tichuTeamA_deactivate();
        }
        if (grandTichuTeamA == 1) {
            grandTichuTeamA_deactivate();
        }

        updateScores();

    }

    private void OneTwoTeamA_deactivate() {

        ToggleButton btn = (ToggleButton) findViewById(R.id.oneTwoTeamA);
        btn.setChecked(false);
        oneTwoTeamA = false;

    }

    private void OneTwoTeamB_deactivate() {

        ToggleButton btn = (ToggleButton) findViewById(R.id.oneTwoTeamB);
        btn.setChecked(false);
        oneTwoTeamB = false;

    }


    public void tichuTeamA_pressed(View view) {

        ToggleButton teamA_tichu = (ToggleButton) findViewById(R.id.tichuTeamA);
        ToggleButton teamB_tichu = (ToggleButton) findViewById(R.id.tichuTeamB);

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
                OneTwoTeamB_deactivate();
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

    public void tichuTeamB_pressed(View view) {

        ToggleButton teamA_tichu = (ToggleButton) findViewById(R.id.tichuTeamA);
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
                OneTwoTeamA_deactivate();
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

    private void tichuTeamA_deactivate() {

        ToggleButton teamA_tichu = (ToggleButton) findViewById(R.id.tichuTeamA);

        tichuTeamA = 0;
        teamA_tichu.setChecked(false);
        teamA_tichu.setTextColor(Color.BLACK);

        updateScores();

    }

    private void tichuTeamB_deactivate() {

        ToggleButton teamB_tichu = (ToggleButton) findViewById(R.id.tichuTeamB);

        tichuTeamB = 0;
        teamB_tichu.setChecked(false);
        teamB_tichu.setTextColor(Color.BLACK);

        updateScores();
    }


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
                OneTwoTeamB_deactivate();
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
            if ( oneTwoTeamB ) {
                OneTwoTeamB_deactivate();
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

    private void grandTichuTeamA_deactivate() {

        ToggleButton teamA_grandTichu = (ToggleButton) findViewById(R.id.grandTichuTeamA);

        grandTichuTeamA = 0;
        teamA_grandTichu.setChecked(false);
        teamA_grandTichu.setTextColor(Color.BLACK);

        updateScores();

    }

    private void grandTichuTeamB_deactivate() {

        ToggleButton teamB_grandTichu = (ToggleButton) findViewById(R.id.grandTichuTeamB);

        grandTichuTeamB = 0;
        teamB_grandTichu.setChecked(false);
        teamB_grandTichu.setTextColor(Color.BLACK);

        updateScores();

    }


}