package com.faw.e115.event;

public class SecondaryOnclickEvent extends BaseEvent {
    public static int FAST = 0;
    public static int MANUAL = 1;
    int type;//0快速入门 1手册
    int index;

    public SecondaryOnclickEvent(int type, int index) {
        this.type = type;
        this.index = index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
