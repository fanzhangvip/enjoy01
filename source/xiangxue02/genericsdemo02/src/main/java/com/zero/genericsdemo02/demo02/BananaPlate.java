package com.zero.genericsdemo02.demo02;

import java.util.ArrayList;
import java.util.List;

public class BananaPlate implements Plate<Banana> {

    private List<Banana> items = new ArrayList<>(10);

    @Override
    public void set(Banana banana) {
        items.add(banana);
    }

    @Override
    public Banana get() {
        return items.get(0);
    }
}
