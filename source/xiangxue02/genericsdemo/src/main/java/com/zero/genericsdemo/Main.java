package com.zero.genericsdemo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

class ViewGroup{}
class LinearLayout extends ViewGroup{}
public class Main {

    public static void main(String[] args) {
        System.out.println("hello world");
        ArrayList arr1 = new ArrayList();
        ArrayList arr2 = new ArrayList();
        Collections.fill(arr1,1);
        Collections.copy(arr1,arr2);
    }

    public <T extends ViewGroup> T getView() {
        return  (T)new LinearLayout();
    }

    public <T extends ViewGroup> ViewGroup getView1() {
        return  new LinearLayout();
    }




}
