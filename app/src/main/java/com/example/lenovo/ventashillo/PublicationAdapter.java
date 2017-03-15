package com.example.lenovo.ventashillo;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.ImageView;

import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by jesus on 18/02/2017.
 */

public class PublicationAdapter extends RecyclerView.ViewHolder{
    public View mView;

    public PublicationAdapter(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setPrice(String price) {
        TextView field = (TextView) mView.findViewById(R.id.cardview_price);
        field.setText(price);
    }

    public void setTitle(String title) {
        TextView field = (TextView) mView.findViewById(R.id.cardview_title);
        field.setText(title);
    }

    public void setCondition(String condition) {
        TextView field = (TextView) mView.findViewById(R.id.cardview_condition);
        field.setText(condition);
    }

    public void setImage(Context cont,String img) {
        ImageView field = (ImageView) mView.findViewById(R.id.img_product);
        //field.setImageResource(img);
        Picasso.with(cont).load(img).into(field);

    }



}
