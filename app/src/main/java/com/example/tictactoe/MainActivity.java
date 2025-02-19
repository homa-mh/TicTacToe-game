package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnSinglePlayer;
    Button btnTwoPlayers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSinglePlayer = findViewById(R.id.btnSinglePlayer);
        btnSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlePlayer = new Intent(MainActivity.this, SinglePlayer.class);
                startActivity(singlePlayer);
            }
        });

        btnTwoPlayers = findViewById(R.id.btnTwoPlayers);
        btnTwoPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent twoPlayers = new Intent(MainActivity.this, TwoPlayers.class);
                startActivity(twoPlayers);
            }
        });
    }
}