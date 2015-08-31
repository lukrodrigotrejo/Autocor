package com.lukrodrigotrejo.autocor.adapter;

/**
 * Created by Luca Rodrigo Trejo on 30/08/2015.
 */
import java.util.ArrayList;
import java.util.HashMap;

import com.lukrodrigotrejo.autocor.util.Constants;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lukrodrigotrejo.autocor.R;

public class ListViewAdapter extends BaseAdapter{
    public ArrayList<HashMap> list;
    Activity activity;
    public ListViewAdapter(Activity activity,ArrayList<HashMap> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_view_adapter_rows, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.codigo);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.descripcion);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);
        holder.txtFirst.setText(map.get(Constants.FIRST_COLUMN).toString());
        holder.txtSecond.setText(map.get(Constants.SECOND_COLUMN).toString());

        return convertView;
    }

}
