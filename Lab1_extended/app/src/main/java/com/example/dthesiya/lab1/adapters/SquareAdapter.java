package com.example.dthesiya.lab1.adapters;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.dthesiya.lab1.MainActivity;
import com.example.dthesiya.lab1.R;
import com.example.dthesiya.lab1.daos.ViewHolder;

import java.util.HashMap;

/**
 * Created by dthesiya on 2/10/17.
 */

public class SquareAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ViewHolder[][] arr;

    public SquareAdapter(Context c) {
        mContext = c;
        Context context = c.getApplicationContext();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 144;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {  // if it's not recycled, initialize some attributes
            rowView = mInflater.inflate(R.layout.square, null);
            ViewHolder viewHolder = new ViewHolder();
            MainActivity.arr[position / 12][position % 12] = viewHolder;
            ImageView iv = (ImageView) rowView.findViewById(R.id.square_background);
            viewHolder.setSquare(iv);
            iv.setImageResource(R.drawable.lightsquare);
            rowView.setTag(viewHolder);
            MainActivity.map.put((position / 12) + "" + (position % 12), viewHolder.isPopulated());
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder vh = (ViewHolder)v.getTag();
                    if(!vh.isPopulated()){
                        vh.getSquare().setImageResource(R.drawable.red);
                    }else{
                        vh.getSquare().setImageResource(R.drawable.lightsquare);
                    }
                    vh.setPopulated(!vh.isPopulated());
                    MainActivity.map.put((position / 12) + "" + (position % 12), vh.isPopulated());
                }
            });
        }
        return rowView;
    }
}
