package com.faw.e115.holder;

import android.content.Context;
import android.view.View;

import com.raizlabs.android.dbflow.structure.BaseModel;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseHolder extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
    }
    public abstract void upDate(Context context, BaseModel model, int position);

}
