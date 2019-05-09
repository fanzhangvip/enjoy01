package com.zero.xuliehuademo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student implements Parcelable {

    private String name;
    private String sax;
    private Integer age;

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    private List<Course> courses;

    private transient Date createTime;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    public Student() {
        System.out.println("_Student: empty");
    }

    /**
     * 需要我们手动创建的构造函数
     * @param name
     * @param sax
     * @param age
     */
    public Student(String name, String sax, Integer age) {
        System.out.println("_Student: " + name + " " + sax + " " + age);
        this.name = name;
        this.sax = sax;
        this.age = age;
        courses = new ArrayList<>();
        createTime = new Date();
    }

    public void newDate() {
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

    /**
     * 内容描述
     * @return
     */
    @Override
    public int describeContents() {
        //几乎都返回 0，除非当前对象中存在文件描述符时为 1
        return 0;
    }

    /**
     * 序列化
     * @param dest
     * @param flags 有两种值，0或1
     * 为1时标识当前对象需要作为返回值返回，不能立刻释放资源，即PARCELABLE_WRITE_RETURN_VALUE
     * 不过几乎所有情况都为0
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.sax);
        dest.writeValue(this.age);
        dest.writeList(this.courses);
    }

    /**
     * 自动创建的的构造器，使用反序列化得到的 Parcel 构造对象
     * 这里面readXXX顺序应该与writeToParcel的writeXXX一一对应，否则，取出来的数据将会出错
     * @param in
     */
    protected Student(Parcel in) {
        this.name = in.readString();
        this.sax = in.readString();
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.courses = new ArrayList<Course>();
        //这里的ClassLoader一定要是一致的
        in.readList(this.courses, Course.class.getClassLoader());

    }

    /**
     * 反序列化
     */
    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            /**
             * 反序列创建对象
             */
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            /**
             * 反序列创建对象数组
             */
            return new Student[size];
        }
    };
}
