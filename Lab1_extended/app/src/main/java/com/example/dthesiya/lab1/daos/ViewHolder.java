package com.example.dthesiya.lab1.daos;

import android.widget.ImageView;
import com.example.dthesiya.lab1.R;

/**
 * Created by dthesiya on 2/10/17.
 */

public class ViewHolder {
    private ImageView square;
    private boolean populated;

    public ImageView getSquare() {
        return square;
    }

    public void setSquare(ImageView square) {
        this.square = square;
    }

    public boolean isPopulated() {
        return populated;
    }

    public void setPopulated(boolean populated) {
        this.populated = populated;
    }

    public void changeImageTo(int id){
        square.setImageResource(id);
    }
}
