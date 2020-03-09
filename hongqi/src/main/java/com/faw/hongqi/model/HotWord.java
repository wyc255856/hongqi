package com.faw.hongqi.model;

import com.faw.hongqi.dbutil.CarDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
@ModelContainer
@Table(database = CarDatabase.class)
public class HotWord  extends BaseModel implements Serializable {
    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private String word;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
