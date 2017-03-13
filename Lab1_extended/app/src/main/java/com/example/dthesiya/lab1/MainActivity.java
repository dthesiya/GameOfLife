package com.example.dthesiya.lab1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.dthesiya.lab1.adapters.SquareAdapter;
import com.example.dthesiya.lab1.daos.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity {

    public static boolean isStarted, flag = true;
    static int stopflag = 0;
    public static HashMap<String, Boolean> map = new HashMap<String, Boolean>();
    public static ViewHolder[][] arr = new ViewHolder[12][12];
    static Button start, next;
    private static GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SquareAdapter sa = new SquareAdapter(this);
        gv = (GridView) findViewById(R.id.chessboard);
        gv.setAdapter(sa);
        start = (Button) findViewById(R.id.play);
        next = (Button) findViewById(R.id.next);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStarted) {
                    setStopped();
                } else {
                    setStarted();
                    //resetting condition flags
                    stopflag = 0;
                    flag = true;
                    //starting ui threads
                    new Thread(){
                        public void run(){
                            try {
                                GamePlay gp = new GamePlay();
                                gp.execute();
                                while(MainActivity.isStarted && MainActivity.flag && MainActivity.stopflag <= 1) {
                                    if(gp.getStatus() == AsyncTask.Status.FINISHED){
                                        gp = new GamePlay();
                                        gp.execute();
                                    }else{
                                        Thread.sleep(300);
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
        });
    }

    public static synchronized void setStopped(){
        for (int index = 0; index < gv.getChildCount(); ++index) {
            View nextChild = gv.getChildAt(index);
            nextChild.setEnabled(true);
        }
        isStarted = false;
        start.setText("Start");
        next.setEnabled(true);
    }

    public static synchronized void setStarted(){
        for (int index = 0; index < gv.getChildCount(); ++index) {
            View nextChild = gv.getChildAt(index);
            nextChild.setEnabled(false);
        }
        start.setText("Stop");
        next.setEnabled(false);
        isStarted = true;
    }
}

class GamePlay extends AsyncTask<Boolean, Integer, Boolean>{

    @Override
    protected Boolean doInBackground(Boolean... flags){
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected synchronized void onPostExecute(Boolean result) {
        boolean tempFlag = false;
        boolean stopflag = true;
        if(MainActivity.isStarted){
            HashMap<String, Boolean> temp = new HashMap<String, Boolean>(MainActivity.map);
            for(int i = 0; i < 12; i ++){
                for(int j = 0; j < 12; j++){
                    boolean isPopulated = MainActivity.map.get(i + "" + j);
                    int neighbours = 0;
                    if(MainActivity.map.containsKey((i - 1) + "" + (j - 1))){
                        neighbours += (MainActivity.map.get((i - 1) + "" + (j - 1))) ? 1 : 0;
                    }
                    if(MainActivity.map.containsKey((i - 1) + "" + (j))){
                        neighbours += (MainActivity.map.get((i - 1) + "" + (j))) ? 1 : 0;
                    }
                    if(MainActivity.map.containsKey((i - 1) + "" + (j + 1))){
                        neighbours += (MainActivity.map.get((i - 1) + "" + (j + 1))) ? 1 : 0;
                    }
                    if(MainActivity.map.containsKey((i) + "" + (j - 1))){
                        neighbours += (MainActivity.map.get((i) + "" + (j - 1))) ? 1 : 0;
                    }
                    if(MainActivity.map.containsKey((i) + "" + (j + 1))){
                        neighbours += (MainActivity.map.get((i) + "" + (j + 1))) ? 1 : 0;
                    }
                    if(MainActivity.map.containsKey((i + 1) + "" + (j - 1))){
                        neighbours += (MainActivity.map.get((i + 1) + "" + (j - 1))) ? 1 : 0;
                    }
                    if(MainActivity.map.containsKey((i + 1) + "" + (j))){
                        neighbours += (MainActivity.map.get((i + 1) + "" + (j))) ? 1 : 0;
                    }
                    if(MainActivity.map.containsKey((i + 1) + "" + (j + 1))){
                        neighbours += (MainActivity.map.get((i + 1) + "" + (j + 1))) ? 1 : 0;
                    }
                    if(isPopulated){
                        if(neighbours < 2 || neighbours > 3){
                            MainActivity.arr[i][j].setPopulated(false);
                            MainActivity.arr[i][j].getSquare().setImageResource(R.drawable.lightsquare);
                            temp.put((i + "" + j), false);
                        }else{
                            tempFlag = true;
                        }
                    }else{
                        if(neighbours == 3){
                            MainActivity.arr[i][j].setPopulated(true);
                            MainActivity.arr[i][j].getSquare().setImageResource(R.drawable.red);
                            temp.put((i + "" + j), true);
                            tempFlag = true;
                            stopflag = false;
                        }
                    }
                }
            }
            MainActivity.map = temp;
            MainActivity.flag = tempFlag;
            MainActivity.stopflag += (stopflag) ? 1 : 0;
            if(!tempFlag || MainActivity.stopflag > 1){
                MainActivity.setStopped();
            }
        }
    }
}