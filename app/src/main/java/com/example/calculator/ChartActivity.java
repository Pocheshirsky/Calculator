package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout chartLayout = new LinearLayout(this);
        Button backButton = new Button(this);
        ChartView chartView = new ChartView(this);

        backButton.setText("Назад");
        backButton.setOnClickListener(view -> backToMain(view));
        chartLayout.setOrientation(LinearLayout.VERTICAL);
        chartLayout.addView(backButton,0);
        chartLayout.addView(chartView,1);

        setContentView(chartLayout);
    }

    private void backToMain(View view) {
        this.finish();
    }
}

class ChartView extends View {
    Paint paint;
    Paint paintGrid;

    public ChartView(Context context) {
        super(context);
        paint = new Paint();
        paintGrid = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawARGB(255,255,255,255);

        int sizeX = canvas.getWidth();
        int sizeY = canvas.getHeight();

        initPaints();
        drawGrid(sizeX, sizeY, canvas);
        drawSinGraph(sizeX, canvas);
    }

    private void initPaints(){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(50);
        paint.setPathEffect(new CornerPathEffect(50));

        paintGrid.setStrokeWidth(1);
        paintGrid.setColor(Color.GRAY);
        paintGrid.setStyle(Paint.Style.STROKE);
        //paintGrid.setPathEffect(new DashPathEffect(new float[]{ 5, 5 }, 0));
    }

    private void drawGrid(int sizeX, int sizeY, Canvas canvas){
        int X = 0;
        int Y = 0;

        for(int i = 0; i < sizeX / 30; i++) {
            canvas.drawLine(X, 0, X, sizeY, paintGrid);
            X += 30;
        }

        for(int i = 0; i < sizeY / 30; i++) {
            canvas.drawLine(0, Y, sizeX, Y, paintGrid);
            Y += 30;
        }
    }

    private void drawSinGraph(int sizeX, Canvas canvas){
        Path path = new Path();
        path.moveTo(0, 300);
        for (float x = (float)(Math.PI / 4); x * 30 < sizeX; x += (Math.PI / 4)) {
            float y = (float)Math.sin(x);
            path.lineTo(x * 30, 300 + y * 100 );
        }
        canvas.drawText("y = Sin(x)", 25, 500, paint);
        canvas.drawPath(path, paint);
    }

    private void drawSomething(int sizeX, Canvas canvas){
        Path path = new Path();
        path.moveTo(0, 600);
        for (int i = 0; i < sizeX; i += 180) {
            path.rQuadTo(45, 100, 90, 0);
            path.rQuadTo(45, -100, 90, 0);
        }
        canvas.drawText("rQuadTo", 25, 200, paint);
        canvas.drawPath(path, paint);
    }
}