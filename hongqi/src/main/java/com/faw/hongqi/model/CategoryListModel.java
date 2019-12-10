package com.faw.hongqi.model;

import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

public class CategoryListModel extends BaseModel {
    private List<CategoryModel> RECORDS;

    public List<CategoryModel> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<CategoryModel> RECORDS) {
        this.RECORDS = RECORDS;
    }
}
