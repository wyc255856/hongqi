package com.faw.hongqi.model;

import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Collection;
import java.util.List;

public class NewsListModel extends BaseModel {
    private List<NewsModel> RECORDS;

    public List<NewsModel> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<NewsModel> RECORDS) {
        this.RECORDS = RECORDS;
    }
}
