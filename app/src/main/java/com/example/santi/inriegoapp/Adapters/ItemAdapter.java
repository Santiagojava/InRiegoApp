package com.example.santi.inriegoapp.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.santi.inriegoapp.Objects.Item;

import java.util.ArrayList;

/**
 * Created by KevinQ on 26/9/2017.
 */

public class ItemAdapter    extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Item> Items;


    public ItemAdapter(Activity activity, ArrayList<Item> items) {
        this.activity = activity;
        Items = items;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Item> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Item> items) {
        Items = items;
    }

    public ItemAdapter() {
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
