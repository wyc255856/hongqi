package com.faw.hongqi.dbutil;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = ColonyDatabase.NAME, version = ColonyDatabase.VERSION)
public class CarDatabase {
    public static final String NAME = "car";

    public static final int VERSION = 2;
}
