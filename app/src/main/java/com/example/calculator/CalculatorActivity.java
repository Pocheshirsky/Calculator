package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    private boolean isSeriousError = false;
    private double NumberA;
    private double NumberB;
    private boolean numberAEditTextIsFocused;
    private boolean numberBEditTextIsFocused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        setNumberAFocusListener();
        setNumberBFocusListener();
    }

    public void showAlert(String errorText, boolean isSerious) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CalculatorActivity.this);
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

    public void add(View view) {
        TextView numberAView = (TextView) findViewById(R.id.numberA);
        TextView numberBView = (TextView) findViewById(R.id.numberB);
        try {
            double numberA = Double.parseDouble(numberAView.getText().toString());
            double numberB = Double.parseDouble(numberBView.getText().toString());
            double result = numberA + numberB;
            TextView resultField = (TextView) findViewById(R.id.resultField);
            resultField.setText(String.valueOf(result));
        }
        catch (Exception e){
            isSeriousError = false;
            showAlert("Введите числа!", isSeriousError);
        }
    }

    public void multiply(View view) {
        TextView numberAView = (TextView) findViewById(R.id.numberA);
        TextView numberBView = (TextView) findViewById(R.id.numberB);
        try{
            double numberA = Double.parseDouble(numberAView.getText().toString());
            double numberB = Double.parseDouble(numberBView.getText().toString());
            double result = numberA * numberB;
            TextView resultField = (TextView) findViewById(R.id.resultField);
            resultField.setText(String.valueOf(result));
        }
        catch (Exception e){
            isSeriousError = false;
            showAlert("Введите числа!", isSeriousError);
        }
    }

    public void pow(View view) {
        TextView numberAView = (TextView) findViewById(R.id.numberA);
        TextView numberBView = (TextView) findViewById(R.id.numberB);
        try {
            double numberA = Double.parseDouble(numberAView.getText().toString());
            double numberB = Double.parseDouble(numberBView.getText().toString());
            double result = Math.pow(numberA, numberB);
            TextView resultField = (TextView) findViewById(R.id.resultField);
            resultField.setText(String.valueOf(result));
        }
        catch (Exception e){
            isSeriousError = false;
            showAlert("Введите числа!", isSeriousError);
        }
    }

    public void clean(View view) {
        TextView numberAView = (TextView) findViewById(R.id.numberA);
        TextView numberBView = (TextView) findViewById(R.id.numberB);
        numberAView.setText("");
        numberBView.setText("");
    }

    public void substract(View view) {
        TextView numberAView = (TextView) findViewById(R.id.numberA);
        TextView numberBView = (TextView) findViewById(R.id.numberB);
        try {
            double numberA = Double.parseDouble(numberAView.getText().toString());
            double numberB = Double.parseDouble(numberBView.getText().toString());
            double result = numberA - numberB;
            TextView resultField = (TextView) findViewById(R.id.resultField);
            resultField.setText(String.valueOf(result));
        }
        catch (Exception e){
            isSeriousError = false;
            showAlert("Введите числа!", isSeriousError);
        }
    }

    public void divide(View view) {
        TextView numberAView = (TextView) findViewById(R.id.numberA);
        TextView numberBView = (TextView) findViewById(R.id.numberB);
        try {
            double numberA = Double.parseDouble(numberAView.getText().toString());
            double numberB = Double.parseDouble(numberBView.getText().toString());
            double result = numberA / numberB;
            TextView resultField = (TextView) findViewById(R.id.resultField);
            resultField.setText(String.valueOf(result));
        }
        catch (Exception e){
            isSeriousError = false;
            showAlert("Введите числа!", isSeriousError);
        }
    }

    public void numberAIsFocused(View view) {}
    public void numberBIsFocused(View view) {}

    public void insertPiNumber(View view) {
        if (numberAEditTextIsFocused){
            EditText numberAView = (EditText) findViewById(R.id.numberA);
            numberAView.setText(String.valueOf(Math.PI));
        }
        if (numberBEditTextIsFocused){
            EditText numberBView = (EditText) findViewById(R.id.numberB);
            numberBView.setText(String.valueOf(Math.PI));
        }
    }

    public void cleanEntry(View view) {
        TextView numberAView = (TextView) findViewById(R.id.numberA);
        TextView numberBView = (TextView) findViewById(R.id.numberB);
        try {
            NumberA = Double.parseDouble(numberAView.getText().toString());
            NumberB = Double.parseDouble(numberBView.getText().toString());
            numberAView.setText("");
            numberBView.setText("");
        }
        catch (Exception e){
            isSeriousError = false;
            showAlert("Введите числа!", isSeriousError);
        }
    }

    public void takeRemainder(View view) {
        TextView numberAView = (TextView) findViewById(R.id.numberA);
        TextView numberBView = (TextView) findViewById(R.id.numberB);
        try {
            double numberA = Double.parseDouble(numberAView.getText().toString());
            double numberB = Double.parseDouble(numberBView.getText().toString());
            double result = numberA % numberB;
            TextView resultField = (TextView) findViewById(R.id.resultField);
            resultField.setText(String.valueOf(result));
        }
        catch (Exception e){
            isSeriousError = false;
            showAlert("Введите числа!", isSeriousError);
        }
    }

    public void saveMemory(View view) {
        TextView numberAView = (TextView) findViewById(R.id.numberA);
        TextView numberBView = (TextView) findViewById(R.id.numberB);
        try {
            NumberA = Double.parseDouble(numberAView.getText().toString());
            NumberB = Double.parseDouble(numberBView.getText().toString());
        }
        catch (Exception e){
            isSeriousError = false;
            showAlert("Введите числа!", isSeriousError);
        }
    }

    public void readMemory(View view) {
        TextView numberAView = (TextView) findViewById(R.id.numberA);
        TextView numberBView = (TextView) findViewById(R.id.numberB);
        if (NumberA==0 && NumberB==0){
            isSeriousError = false;
            showAlert("В памяти нет чисел!", isSeriousError);
        }
        try {
            numberAView.setText(String.valueOf(NumberA));
            numberBView.setText(String.valueOf(NumberB));
        }
        catch (Exception e){
            isSeriousError = false;
            showAlert("В памяти нет чисел!", isSeriousError);
        }
    }

    public void cleanMemory(View view) {
        NumberA = 0;
        NumberB = 0;
    }

    private void setNumberAFocusListener () {
        EditText numberAView = (EditText)findViewById(R.id.numberA);
        numberAView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus)
                    numberAEditTextIsFocused = true;
                else {
                    numberAEditTextIsFocused = false;
                }
            }
        });
    }

    private void setNumberBFocusListener () {
        EditText numberAView = (EditText)findViewById(R.id.numberB);
        numberAView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus)
                    numberBEditTextIsFocused = true;
                else {
                    numberBEditTextIsFocused = false;
                }
            }
        });
    }

    public void backToMain(View view) {
        this.finish();
    }
}