package com.android.blocker.helper;

public class DataTypeManip {

    public static int toInt(boolean bool) {

        if (bool)
            return 1;

        return 0;
    }

    public static boolean toBool(int anInt) {

        if (anInt == 1)
            return true;

        return false;
    }

}
