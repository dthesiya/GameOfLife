package com.example.dthesiya.lab1;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.dthesiya.lab1.R;

public class MainActivity extends AppCompatActivity {

    private Button reset, next;
    private ViewHoldr vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vh = (ViewHoldr) findViewById(R.id.gameboard);
        vh.setGridX(getResources().getInteger(R.integer.grid_x));
        vh.setGridY(getBaseContext().getResources().getInteger(R.integer.grid_y));
        reset = (Button) findViewById(R.id.reset);
        next = (Button) findViewById(R.id.next);
        final TextView tv = (TextView) findViewById(R.id.label);
        tv.setText("" + getResources().getText(R.string.seekbar_value) + 12);
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekbar);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue = 12;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < 2){
                    progressValue = 2;
                    seekBar.setProgress(2);
                }else{
                    progressValue = progress;
                }
                vh.setGridX(progressValue);
                vh.setGridY(progressValue);
                tv.setText("" + getResources().getText(R.string.seekbar_value) + progressValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                vh.setGridX(progressValue);
                vh.setGridY(progressValue);
                tv.setText("" + getResources().getText(R.string.seekbar_value) + progressValue);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Do you really want to reset?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                vh.resetBoard();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.generateNextGen();
            }
        });
    }
}