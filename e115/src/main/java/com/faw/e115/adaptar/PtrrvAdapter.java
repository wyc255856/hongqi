package com.faw.e115.adaptar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.faw.e115.holder.BaseHolder;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class PtrrvAdapter<T extends BaseHolder, K extends BaseModel> extends PtrrvBaseAdapter {
    int layoutId;
    Class<T> clazz;
    public List<K> list = new ArrayList<>();
    Context context;

    public PtrrvAdapter(Context context, int layoutID, Class<T> clazz) {

        super(context);

        this.context = context;
        layoutId = layoutID;
        this.clazz = clazz;
    }


    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layoutId, null);
        return newTclass(this.clazz, view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T viewHoleder = (T) holder;
        viewHoleder.upDate(context, list.get(position), position);
    }


    private <T> T newTclass(Class<T> clazz, View view) {
        T a = null;
        try {
            Constructor c1 = clazz.getDeclaredConstructor(new Class[]{View.class});
            c1.setAccessible(true);
            try {
                a = (T) c1.newInstance(new Object[]{view});
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;

    }

    public void refreshData(List<K> arg0) {

        list.clear();
        list.addAll(arg0);
        setCount(list.size());
        notifyDataSetChanged();
    }

    public void addData(List<K> arg0) {
        list.addAll(arg0);
        setCount(list.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}