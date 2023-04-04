package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase chartDB = getBaseContext().openOrCreateDatabase("Chart.db", MODE_PRIVATE, null);

        LinearLayout chartLayout = new LinearLayout(this);
        Button backButton = new Button(this);
        ChartView chartView = new ChartView(this, chartDB);

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
    SQLiteDatabase chartDB;

    public ChartView(Context context, SQLiteDatabase chartDB) {
        super(context);
        paint = new Paint();
        paintGrid = new Paint();
        this.chartDB = chartDB;
        initDB();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawARGB(255,255,255,255);

        int sizeX = canvas.getWidth();
        int sizeY = canvas.getHeight();

        initPaints();
        List<PointF> points = new ArrayList<>();
        drawGrid(sizeX, sizeY, canvas);
        drawSinGraphAndSetInDB(canvas, sizeX, sizeY, points);

        saveToDB(points, "points");

        Paint paintPoints = new Paint();
        paintPoints.setColor(Color.RED);
        paintPoints.setStrokeWidth(10);

        List<PointF> minAndMaxPoints = getMinAndMax();
        for (PointF point : minAndMaxPoints)
            canvas.drawPoint(point.x, point.y, paintPoints);
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

    private void drawSinGraphAndSetInDB(Canvas canvas, int sizeX, int sizeY, List<PointF> points){
        float x = 0;
        Path path = new Path();
        path.moveTo(0, 200);
        while (true) {
            PointF p1 = new PointF((x * 20), (float) (200 - 50 * Math.sin(x)));
            x += Math.PI / 10;
            PointF p2 = new PointF((x * 20), (float) (200 - 50 * Math.sin(x)));
            x += Math.PI / 10;
            path.quadTo(p1.x, p1.y, p2.x, p2.y);

            points.add(p1);
            points.add(p2);

            if(p1.x > sizeX || p2.x > sizeX)
                break;
        }
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

    private void initDB() {
        chartDB.execSQL("CREATE TABLE IF NOT EXISTS points (x REAL, y REAL)");
        chartDB.execSQL("CREATE TABLE IF NOT EXISTS min_max (x REAL, y REAL)");
        chartDB.execSQL("DELETE FROM points");
        chartDB.execSQL("DELETE FROM min_max");
    }

    private void saveToDB(List<PointF> pointsList, String tableName) {
        for (PointF point : pointsList) {
            ContentValues values = new ContentValues();
            values.put("x", point.x);
            values.put("y", point.y);
            chartDB.insert(tableName, null, values);
        }
    }

    private List<PointF> getMinAndMax() {
        Cursor cursor = chartDB.rawQuery("SELECT * FROM points", null);
        List<PointF> points = new ArrayList<>();
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            PointF point = new PointF();
            point.x = cursor.getFloat(0);
            point.y = cursor.getFloat(1);

            points.add(point);
            cursor.moveToNext();
        }

        List<PointF> minAndMaxPoint = new ArrayList<>();
        double maxY = points.get(0).y;
        double minY = points.get(0).y;
        for (PointF point : points) {
            if (point.y > maxY)
                maxY = point.y;
            if (point.y < minY)
                minY = point.y;
        }

        for (PointF point : points)
            if (point.y == maxY || point.y == minY)
                minAndMaxPoint.add(point);

        saveToDB(minAndMaxPoint, "min_and_max");
        return minAndMaxPoint;
    }
}