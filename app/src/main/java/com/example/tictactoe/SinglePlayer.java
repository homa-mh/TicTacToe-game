package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class SinglePlayer extends AppCompatActivity {

    private ImageButton[][] btnCells = new ImageButton[3][3];
    private ImageButton btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;
    private String[][] cells = new String[3][3];
    private TextView txtResult;
    private String o = "o";
    private String x = "x";
    private Button btnReset;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        txtResult = findViewById(R.id.txtResult);
        btn00 = findViewById(R.id.btn11);
        btn01 = findViewById(R.id.btn12);
        btn02 = findViewById(R.id.btn13);
        btn10 = findViewById(R.id.btn21);
        btn11 = findViewById(R.id.btn22);
        btn12 = findViewById(R.id.btn23);
        btn20 = findViewById(R.id.btn31);
        btn21 = findViewById(R.id.btn32);
        btn22 = findViewById(R.id.btn33);
        btnCells = new ImageButton[][]{{btn00, btn01, btn02}, {btn10, btn11, btn12}, {btn20, btn21, btn22}};
        cells = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};


        for(int i = 0 ; i < 3 ; i++){
            for(int j = 0 ; j < 3 ; j++){
                int finalJ = j;
                int finalI = i;
                btnCells[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userMove(finalI, finalJ);
                    }
                });
            }
        }



        btnReset = findViewById(R.id.btnReset1);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cells =  new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};
                for(int i = 0 ; i < 3 ; i++){
                    for(int j = 0 ; j < 3 ; j++){
                        btnCells[i][j].setImageResource(0);
                    }
                }
            }
        });

    }

    private String winnerCheck(String[][] cells){
        for(String[] row : cells)
            if(Objects.equals(row[0], row[1]) && Objects.equals(row[1], row[2]) && !Objects.equals(row[2], ""))
                return row[0];
        for(int i = 0; i < 3; i++)
            if(Objects.equals(cells[0][i], cells[1][i]) && Objects.equals(cells[1][i], cells[2][i]) && !Objects.equals(cells[2][i], ""))
                return cells[0][i];
        if(Objects.equals(cells[0][0], cells[1][1]) && Objects.equals(cells[1][1], cells[2][2]) && !Objects.equals(cells[2][2], ""))
            return cells[1][1];
        if(Objects.equals(cells[0][2], cells[1][1]) && Objects.equals(cells[1][1], cells[2][0]) && !Objects.equals(cells[2][2], ""))
            return cells[1][1];
        int emptyCell = 0;
        for(int i = 0 ; i < 3 ; i++){
            for(int j = 0 ; j < 3 ; j++){
                if (Objects.equals(cells[i][j], "")){
                    emptyCell++;
                }
            }
        }
        if(emptyCell == 0){
            return "tie";
        }
        return null;
    }
    private boolean gameEnd(){

        String checkWinner = winnerCheck(cells);
        if (Objects.equals(checkWinner, "tie")) {
            txtResult.setText("Ooh. We tied. 🤝🏻");
            return true;
        }
        else if(Objects.equals(checkWinner, x)){
            txtResult.setText("🎉 Wow! You Win! 🎊");
            cells = new String[][]{{"x", "x", "x"}, {"x", "x", "x"}, {"x", "x", "x"}};
            return true;
        }
        else if((Objects.equals(checkWinner, o))){
            txtResult.setText("Sorry kiddo. You Lose! 😏");
            cells = new String[][]{{"x", "x", "x"}, {"x", "x", "x"}, {"x", "x", "x"}};
            return true;
        }
        else {
            txtResult.setText("Your Turn ☺");
            return false;
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void userMove(int rowNum, int colNum){
        if(Objects.equals(cells[rowNum][colNum], "")){
            cells[rowNum][colNum] = x;
            btnCells[rowNum][colNum].setImageResource(R.drawable.x);
            if(!gameEnd())
                computerMove();
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void computerMove(){
        double bestScore = Double.NEGATIVE_INFINITY;
        double score = 0;
        int row = 0;
        int column = 0;
        for(int i = 0 ; i < 3 ; i++){
            for(int j = 0 ; j < 3 ; j++){
                if(Objects.equals(cells[i][j], "")){
                    cells[i][j] = o;
                    score = minimax(cells, false);
                    cells[i][j] = "";
                    if(score > bestScore) {
                        bestScore = score;
                        row = i;
                        column = j;
                    }
                }
            }
        }
        cells[row][column] = o;
        btnCells[row][column].setImageResource(R.drawable.o);
        gameEnd();
    }
    private double minimax(String[][] cells, boolean maximize){
        double bestScore;
        double score = 0;
        String checkWinner = winnerCheck(cells);
        if (Objects.equals(checkWinner, "tie"))
            return 0;
        if(Objects.equals(checkWinner, x))
            return -10;
        if (Objects.equals(checkWinner, o))
            return 10;

        if (maximize){
            bestScore = Double.NEGATIVE_INFINITY;
            for(int i = 0 ; i < 3 ; i++) {
                for (int j = 0; j < 3; j++) {
                    if(Objects.equals(cells[i][j], "")) {
                        cells[i][j] = o;
                        score = minimax(cells, false);
                        cells[i][j] = "";
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
        else{
            bestScore = Double.POSITIVE_INFINITY;
            for(int i = 0 ; i < 3 ; i++) {
                for (int j = 0; j < 3; j++) {
                    if(Objects.equals(cells[i][j], "")) {
                        cells[i][j] = x;
                        score = minimax(cells, true);
                        cells[i][j] = "";
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
            }
    }

}