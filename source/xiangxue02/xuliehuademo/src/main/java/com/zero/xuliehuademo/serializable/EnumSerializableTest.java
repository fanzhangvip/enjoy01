package com.zero.xuliehuademo.serializable;

 enum Num1{
    ONE,TWO,THREE;

    public void printValues(){
        System.out.println("ONE: "+ ONE.ordinal() + ", TWO: " + TWO.ordinal() + ", THREE: " + THREE.ordinal());
    }
}
public class EnumSerializableTest {

    public static void main(String[] args) throws Exception {
        byte[] bs =SerializeableUtils.serialize(Num1.THREE);
        System.out.println("反序列化后");
        Num1 s1 = SerializeableUtils.deserialize(bs);
        s1.printValues();
    }

    public static void EnumTestJava() throws Exception {
        byte[] bs =SerializeableUtils.serialize(Num1.THREE);
        Num1.THREE.printValues();
        System.out.println("反序列化后");
        Num1 s1 = SerializeableUtils.deserialize(bs);
        s1.printValues();
    }
}
