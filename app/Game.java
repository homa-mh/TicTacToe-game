import android.annotation.SuppressLint;
import android.widget.Toast;

import com.example.tictactoe.R;

import java.util.Objects;

public class Game {

//  checking if the game has finished
    private boolean gameEnd(){

        String checkWinner = winnerCheck(cells);
        if (Objects.equals(checkWinner, "tie")) {
            txtResult.setText("Ooh. We tied. 🤝🏻");
            return true;
        }
        else if(Objects.equals(checkWinner, x)){
            txtResult.setText("🎉 Wow! You Win! 🎊");
            return true;
        }
        else if((Objects.equals(checkWinner, o))){
            txtResult.setText("Sorry kiddo. You Lose! 😏");
            return true;
        }
        else {
            txtResult.setText("Your Turn ☺");
            return false;
        }
    }

    //  searching for a winner
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
//  when user press image buttons in single player mode
    @SuppressLint("UseCompatLoadingForDrawables")
    private void userMove(int rowNum, int colNum){
        if(Objects.equals(cells[rowNum][colNum], "")){
            cells[rowNum][colNum] = x;
            btnCells[rowNum][colNum].setImageResource(R.drawable.x);
            if(!gameEnd())
                computerMove();
        }
        else {
            Toast.makeText(this, "It's already full!", Toast.LENGTH_SHORT).show();
        }
    }
//  AI move
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
