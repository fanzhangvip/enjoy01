package com.zero.xiangxue02;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Test {
    //PESC

    public <T extends  ViewGroup> T getView(Context context) {
        return (T) new LinearLayout(context);
    }

    public <T extends ViewGroup>T getView1(Context context) {
        return (T) new LinearLayout(context);
    }

    public static void main(String[] args) {
        Test t = new Test();
        ViewGroup viewGroup = t.getView(null);
    }

}
