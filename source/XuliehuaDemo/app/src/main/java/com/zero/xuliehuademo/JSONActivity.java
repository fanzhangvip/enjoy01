package com.zero.xuliehuademo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zero.xuliehuademo.xml.Course;
import com.zero.xuliehuademo.xml.Student;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 性能测试
 */
public class JSONActivity extends AppCompatActivity {
    private static final String TAG = "Zero";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_json1)
    public void onBtnDomClicked() {
        try {
            createJson(JSONActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_json2)
    public void onBtnSaxClicked() {
        try {
            parseJson(JSONActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createJson(Context context) throws Exception {
        File file = new File(getFilesDir(), "orgjson.json");//获取到应用在内部的私有文件夹下对应的orgjson.json文件
        JSONObject student = new JSONObject();//实例化一个JSONObject对象
        student.put("name", "OrgJson");//对其添加一个数据
        student.put("sax", "男");
        student.put("age", 23);
        JSONObject course1 = new JSONObject();
        course1.put("name", "语文");
        course1.put("score", 98.2f);
        JSONObject course2 = new JSONObject();
        course2.put("name", "数学");
        course2.put("score", 93.2f);
        JSONArray coures = new JSONArray();//实例化一个JSON数组
        coures.put(0, course1);//将course1添加到JSONArray，下标为0
        coures.put(1, course2);
        //然后将JSONArray添加到名为student的JSONObject
        student.put("courses", coures);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(student.toString().getBytes());
        fos.close();
        Log.i(TAG, "createJson: " + student.toString());
        Toast.makeText(context, "创建成功", Toast.LENGTH_LONG).show();
    }

    private void parseJson(Context context) throws Exception {
        File file = new File(getFilesDir(), "orgjson.json");
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringBuffer sb = new StringBuffer();

        while (null != (line = br.readLine())) {
            sb.append(line);
        }
        fis.close();
        isr.close();
        br.close();

        Student student = new Student();
        //利用JSONObject进行解析
        JSONObject stuJsonObject = new JSONObject(sb.toString());
        //为什么不用getString?
        //optString会在得不到你想要的值时候返回空字符串""，而getString会抛出异常
        String name = stuJsonObject.optString("name", "zero");
        student.setName(name);
        student.setSax(stuJsonObject.optString("sax", "男"));
        student.setAge(stuJsonObject.optInt("age", 18));

        //获取数组数据
        JSONArray couresJson = stuJsonObject.optJSONArray("courses");

        for (int i = 0; i < couresJson.length(); i++) {
            JSONObject courseJsonObject = couresJson.getJSONObject(i);
            Course course = new Course();
            course.setName(courseJsonObject.optString("name", ""));
            course.setScore((float) courseJsonObject.optDouble("score", 0));
            student.addCourse(course);
        }

        Log.i(TAG, "parseJson: " + student);
        Toast.makeText(context, "解析成功", Toast.LENGTH_LONG).show();

    }

}
