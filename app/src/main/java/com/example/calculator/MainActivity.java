package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.util.DisplayMetrics;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics display = getResources().getDisplayMetrics();
        if ((display.heightPixels>=1920 & display.widthPixels>=1080)||
                (display.heightPixels>=1080 & display.widthPixels>=1920)) {
            showAlert("Разрешение экрана не 1920х1080.", false);
        }
    }

    public void showAlert(String errorText, boolean isSerious) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Ошибка!");
        builder.setMessage(errorText);
        builder.setCancelable(false);
        if (isSerious)
            builder.setNegativeButton("Выход", (dialog, id) -> killProcess());
        else
            builder.setNegativeButton("Закрыть", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void close(View view) {
        this.finishAffinity();
    }

    public void openCalculator(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void openChart(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }
}


