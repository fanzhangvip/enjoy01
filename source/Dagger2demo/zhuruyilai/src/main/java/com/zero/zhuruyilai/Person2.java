package com.zero.zhuruyilai;

public class Person2  implements DepedencySetter {

    //接口方式注入
    @Override
    public void set(Driveable driveable) {
        this.mDriveable = mDriveable;
    }

    private Driveable mDriveable;

    //构造函数注入
    public Person2(Driveable driveable){
        this.mDriveable = driveable;
    }

    //setter 方式注入
    public void setDriveable(Driveable mDriveable) {
        this.mDriveable = mDriveable;
    }

    public void goOut(){
        System.out.println("出门啦");
        mDriveable.drive();
        //mCar.drive();
//        mTrain.drive();
    }

    public static void main(String ... args){
            //TODO:
        Person2 person = new Person2(new Car());
        person.goOut();
    }
}
