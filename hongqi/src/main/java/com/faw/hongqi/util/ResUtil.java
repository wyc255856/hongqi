package com.faw.hongqi.util;


import com.faw.hongqi.R;

import java.lang.reflect.Field;

public class ResUtil {
    public static int getMipmapResId(String variableName) {
        try {
            Field idField = R.mipmap.class.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getDrawableResId(String variableName) {
        try {
            Field idField = R.drawable.class.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
