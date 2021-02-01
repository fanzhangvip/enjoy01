package com.example.xuliehualib;

import java.io.Serializable;

class BaseB {

    public BaseB() {
    }

    public BaseB(String mytring, int myInt) {
        this.mytring = mytring;
        this.myInt = myInt;
    }

    private String mytring;
    private int myInt;

    public String getMytring() {
        return mytring;
    }

    public void setMytring(String mytring) {
        this.mytring = mytring;
    }

    public int getMyInt() {
        return myInt;
    }

    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    @Override
    public String toString() {
        return "A{" +
                "mytring='" + mytring + '\'' +
                ", myInt=" + myInt +
                '}';
    }
}

class B extends BaseB implements Serializable {
    private String mTest;
    private double mDouble;

    public B(String mTest, double mDouble) {
        super("parent",1);
        this.mTest = mTest;
        this.mDouble = mDouble;
    }

    public String getmTest() {
        return mTest;
    }

    public void setmTest(String mTest) {
        this.mTest = mTest;
    }

    public double getmDouble() {
        return mDouble;
    }

    public void setmDouble(double mDouble) {
        this.mDouble = mDouble;
    }


    @Override
    public String toString() {
        return "B{" +
                "mTest='" + mTest + '\'' +
                ", mDouble=" + mDouble +
                "} " + super.toString();
    }
}

public class Main {


    public static void main(String[] args) throws Exception {



        B b = new B("Testb",32);
        System.out.println(b);

        String path = System.getProperty("user.dir") + "a.out";
        SerializeableUtils.saveObject(b,path);

        B b1 = SerializeableUtils.readObject(path);
        System.out.println(b1);

    }
}
