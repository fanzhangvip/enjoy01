package com.zero.xuliehuademo.serializable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class _Student implements Serializable {

    private static final long serialVersionUID = -2100492893943893602L;
    private String name;
    private String sax;
    private Integer age;

    public List<_Course> getCourses() {
        return courses;
    }

    public void addCourse(_Course course) {
        this.courses.add(course);
    }

    private List<_Course> courses;

    //用transient关键字标记的成员变量不参与序列化(在被反序列化后，transient 变量的值被设为初始值，如 int 型的是 0，对象型的是 null)
    private transient Date createTime;
    //静态成员变量属于类不属于对象，所以不会参与序列化(对象序列化保存的是对象的“状态”，也就是它的成员变量，因此序列化不会关注静态变量)
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    public _Student(){
        System.out.println("_Student: empty");
    }

    public _Student(String name, String sax, Integer age) {
        System.out.println("_Student: "+ name + " " + sax + " " + age);
        this.name = name;
        this.sax = sax;
        this.age = age;
        courses = new ArrayList<>();
    }

    public void newDate(){
        createTime = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSax() {
        return sax;
    }

    public void setSax(String sax) {
        this.sax = sax;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "_Student{" +
                "name='" + name + '\'' +
                ", sax='" + sax + '\'' +
                ", age=" + age +
                ", courses=" + courses +
                '}';
    }
}
