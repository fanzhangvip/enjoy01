package com.zero.xuliehuademo.serializable;

import java.io.Serializable;

public class _Course implements Serializable {

    private static final long serialVersionUID = 667279791530738499L;
    private String name;
    private float score;

    public _Course(){
        System.out.println("_Course: empty");
    }

    public _Course(String name, float score) {
        System.out.println("_Course: " + name+ " " + score);
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "_Course{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
