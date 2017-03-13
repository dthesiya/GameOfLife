package com.example.dthesiya.lab1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

/**
 * Created by dthesiya on 2/10/17.
 */

public class ViewHoldr extends View{

    private boolean[][] populated;
    private int grid_x, grid_y, width, height;
    private int cellHeight, cellWidth;
    private Paint paint = new Paint();

    public ViewHoldr(Context context) {
        this(context, null);
    }

    public ViewHoldr(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setGridX(int x){
        grid_x = x;
    }

    public void setGridY(int y){
        grid_y = y;
        populated = new boolean[grid_y][grid_x];
        height = width = getWidth();
        cellWidth = width / grid_x;
        cellHeight = height / grid_y;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = width = getWidth();
        cellWidth = width / grid_x;
        cellHeight = height / grid_y;
        populated = new boolean[grid_y][grid_x];
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        paint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawRect(0, 0, width, height, paint);

        for (int i = 0; i < grid_y; i++) {
            for (int j = 0; j < grid_x; j++) {
                if (populated[i][j]) {
                    int circle_x = i * cellWidth + cellWidth / 2 ;
                    int circle_y = j * cellHeight +  cellHeight / 2;
                    paint.setColor(Color.RED);
                    canvas.drawCircle(circle_x, circle_y, (cellHeight / 2) - 4, paint);
                    paint.setColor(Color.BLACK);
                }
            }
        }

        paint.setColor(Color.BLACK);
        for (int i = 1; i < grid_x; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, paint);
        }

        for (int i = 1; i < grid_y; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellHeight, height, paint);
        }

        canvas.drawLine(0, width, width, width, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / cellWidth);
            int y = (int) (event.getY() / cellHeight);

            if (x < grid_x && y < grid_y) {
                populated[x][y] = !populated[x][y];
                invalidate();
            }
        }

        return true;
    }

    public void generateNextGen(){
        boolean[][] temp_status = copy(populated);
        for(int i = 0; i < grid_x; i ++){
            for(int j = 0; j < grid_y; j++){
                boolean isPopulated = populated[i][j];
                int neighbours = 0;
                if(i > 0 && j > 0){
                    neighbours += (populated[i - 1][j - 1]) ? 1 : 0;
                }
                if(i > 0){
                    neighbours += (populated[i - 1][j]) ? 1 : 0;
                }
                if(i > 0 && j < grid_y - 1){
                    neighbours += (populated[i - 1][j + 1]) ? 1 : 0;
                }
                if(j > 0){
                    neighbours += (populated[i][j - 1]) ? 1 : 0;
                }
                if(j < grid_y - 1){
                    neighbours += (populated[i][j + 1]) ? 1 : 0;
                }
                if(i < grid_x - 1 && j > 0){
                    neighbours += (populated[i + 1][j - 1]) ? 1 : 0;
                }
                if(i < grid_x - 1){
                    neighbours += (populated[i + 1][j]) ? 1 : 0;
                }
                if(i < grid_x - 1 && j < grid_y - 1){
                    neighbours += (populated[i + 1][j + 1]) ? 1 : 0;
                }
                if(isPopulated){
                    if(neighbours < 2 || neighbours > 3){
                        temp_status[i][j] = false;
                    }
                }else{
                    if(neighbours == 3){
                        temp_status[i][j] = true;
                    }
                }
            }
        }
        populated = temp_status;
        invalidate();
    }

    public void resetBoard(){
        for(int i = 0; i < grid_x; i ++) {
            for (int j = 0; j < grid_y; j++) {
                populated[i][j] = false;
                invalidate();
            }
        }
    }

    public boolean[][] copy(boolean[][] input) {
        boolean[][] target = new boolean[input.length][];
        for (int i=0; i <input.length; i++) {
            target[i] = Arrays.copyOf(input[i], input[i].length);
        }
        return target;
    }
}
