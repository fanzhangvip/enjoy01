package com.zero.zhuruyilai;

public class Person {

//    private Bike mBike;
    private Car mCar;
    private Train mTrain;
    private Driveable mDriveable;

    public Person(){
//        mBike = new Bike();
        //mCar = new Car();
       mDriveable = new Train();
    }

    public void goOut(){
        System.out.println("出门啦");
        mDriveable.drive();
        //mCar.drive();
//        mTrain.drive();
    }

    public static void main(String ... args){
            //TODO:
        Person person = new Person();
        person.goOut();
    }
}
