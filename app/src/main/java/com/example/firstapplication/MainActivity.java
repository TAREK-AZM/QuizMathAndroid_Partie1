package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtNumber1, txtNumber2, txtResult;
    Button btnAdd, btnSubtract, btnMultiply, btnGenerate;

    int n1, n2;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link XML → Java
        txtNumber1 = findViewById(R.id.txtNumber1);
        txtNumber2 = findViewById(R.id.txtNumber2);
        txtResult = findViewById(R.id.txtResult);

        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnGenerate = findViewById(R.id.btnGenerate);

        // First numbers
        generateNumbers();

        // Events
        btnAdd.setOnClickListener(v -> {
            txtResult.setText("Résultat : " + (n1 + n2));
        });

        btnSubtract.setOnClickListener(v -> {
            txtResult.setText("Résultat : " + (n1 - n2));
        });

        btnMultiply.setOnClickListener(v -> {
            txtResult.setText("Résultat : " + (n1 * n2));
        });

        btnGenerate.setOnClickListener(v -> generateNumbers());
    }

    private void generateNumbers() {
        n1 = random.nextInt(889) + 111;
        n2 = random.nextInt(889) + 111;

        txtNumber1.setText(String.valueOf(n1));
        txtNumber2.setText(String.valueOf(n2));
        txtResult.setText("Résultat : ");
    }

}