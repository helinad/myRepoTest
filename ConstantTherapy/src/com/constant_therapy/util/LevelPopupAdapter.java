package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.constant_therapy.constantTherapy.R;
  
public class LevelPopupAdapter extends BaseAdapter {  
  
    private Context context;  
  
    private LinkedList<String> list;  
    private int mItemSelected = -1 ;
    public void setItemSelected(int position){
    mItemSelected=position;
    }
    public LevelPopupAdapter(Context context, LinkedList<String> groups) {  
  
        this.context = context;  
        this.list = groups;  
  
    }  
  
    @Override  
    public int getCount() {  
        return list.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
  
        return list.get(position);  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup viewGroup) {  
  
          
        ViewHolder holder;  
        if (convertView==null) {  
            convertView=LayoutInflater.from(context).inflate(R.layout.level_popupinflate, null);  
            holder=new ViewHolder();  
              
            convertView.setTag(holder);  
              
            holder.groupItem=(TextView) convertView.findViewById(R.id.tvtitle); 
           // holder.ckvalue=(CheckBox) convertView.findViewById(R.id.radioButton1);
            
              
        }  
        else{  
            holder=(ViewHolder) convertView.getTag();  
        }  
        if(mItemSelected==position){
        	convertView.setBackgroundColor(Color.parseColor("#047CFF"));
        }else{
        	convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.groupItem.setTextColor(Color.BLACK);  
        holder.groupItem.setText(list.get(position));  
          notifyDataSetChanged();
        return convertView;  
    }  
  
    static class ViewHolder {  
        TextView groupItem;
        CheckBox ckvalue;
    }  
  
}  