package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class TwoPlayers extends AppCompatActivity {

    private ImageButton[][] btnCells = new ImageButton[6][6];
    private ImageButton btn00, btn01, btn02,
            btn10, btn11, btn12,
            btn20, btn21, btn22;
    private String[][] cells = new String[3][3];
    private TextView txtResult;
    private String o = "o";
    private String x = "x";
    private String turn = x;
    private Button btnReset;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players);


        txtResult = findViewById(R.id.txtResult2);
        btn00 = findViewById(R.id.btn1);
        btn01 = findViewById(R.id.btn2);
        btn02 = findViewById(R.id.btn3);
        btn10 = findViewById(R.id.btn4);
        btn11 = findViewById(R.id.btn5);
        btn12 = findViewById(R.id.btn6);
        btn20 = findViewById(R.id.btn7);
        btn21 = findViewById(R.id.btn8);
        btn22 = findViewById(R.id.btn9);


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



        btnReset = findViewById(R.id.btnReset2);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cells = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};
                for(int i = 0 ; i < 3 ; i++){
                    for(int j = 0 ; j < 3 ; j++){
                        btnCells[i][j].setImageResource(0);
                    }
                }
            }
        });

    }


//  checking if the game has finished
    private boolean gameEnd(){

        String checkWinner = winnerCheck(cells);
        if (Objects.equals(checkWinner, "tie")) {
            txtResult.setText("Ooh. You tied. 🤝🏻");
            return true;
        }
        else if(Objects.equals(checkWinner, x)){
            txtResult.setText("🎉 X win! 🎊");
            cells = new String[][]{{"x", "x", "x"}, {"x", "x", "x"}, {"x", "x", "x"}};
            return true;
        }
        else if((Objects.equals(checkWinner, o))){
            txtResult.setText("🎉O win!🎈");
            cells = new String[][]{{"x", "x", "x"}, {"x", "x", "x"}, {"x", "x", "x"}};
            return true;
        }
        else if (Objects.equals(turn, x)){
            txtResult.setText("It's X's turn!");
            return false;
        }
        else {
            txtResult.setText("It's O's turn!");
            return false;
        }
    }
    //  searching for a winner
    private String winnerCheck(String[][] cells){
        for(String[] row : cells)
            if(Objects.equals(row[0], row[1]) && Objects.equals(row[1], row[2]) && !Objects.equals(row[2], ""))
                return row[0];
        for(int i = 0; i < 3; i++)
            if(Objects.equals(cells[0][i], cells[1][i]) && Objects.equals(cells[1][i], cells[2][i])
                    && !Objects.equals(cells[2][i], ""))
                return cells[0][i];
        if(Objects.equals(cells[0][0], cells[1][1]) && Objects.equals(cells[1][1], cells[2][2])
                 &&  !Objects.equals(cells[2][2], ""))
            return cells[1][1];
        if(Objects.equals(cells[0][2], cells[1][1]) && Objects.equals(cells[1][1], cells[2][0])
                && !Objects.equals(cells[1][1], ""))
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
    @SuppressLint("UseCompatLoadingForDrawables")
    private void userMove(int rowNum, int colNum){
        if(Objects.equals(cells[rowNum][colNum], "")){
            cells[rowNum][colNum] = turn;
            if(Objects.equals(turn, x)){
                btnCells[rowNum][colNum].setImageResource(R.drawable.x);
                turn = o;
            }
            else {
                btnCells[rowNum][colNum].setImageResource(R.drawable.o);
                turn = x;
            }
            gameEnd();
        }
    }
}