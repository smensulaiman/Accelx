package com.example.accels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.accels.Item.Test;
import com.example.accels.R;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    Context mcontext;
    List <Test> mList;

    public GridAdapter(Context context,List<Test>list){
        this.mcontext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.grid_item, parent, false);
            TextView name = convertView.findViewById(R.id.productName);
            TextView price = convertView.findViewById(R.id.productPrice);
            name.setText(mList.get(position).getTitle().substring(1,8));
            //price.setText(String.valueOf(mList.get(position).getId()));
        }
        return convertView;
    }
}
