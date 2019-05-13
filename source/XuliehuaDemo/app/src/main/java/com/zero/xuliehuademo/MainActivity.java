package com.zero.xuliehuademo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zero.xuliehuademo.parcelable.Course;
import com.zero.xuliehuademo.parcelable.Student;
import com.zero.xuliehuademo.serializable._Course;
import com.zero.xuliehuademo.serializable._Student;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 性能测试
 */
public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tvResultP)
    TextView tvResultP;

    @BindView(R.id.tvResultS)
    TextView tvResultS;

    @BindView(R.id.tvResult)
    TextView tvResult;

    private Student student;
    private _Student _student;
    private long totalTestTime;

    private long perParcelabel;
    private long perSerializable;
    private long perEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initStudent();
        init_Student();
    }

    @OnClick(R.id.tvTest)
    public void startTest() {
        totalTestTime = 0;
        perParcelabel = 0;
        perSerializable = 0;
        perEmpty = 0;
        testEmpty();
    }

    private void testEmpty() {
        Log.i("Zero", "testEmpty: ");
        Constant.MODE = 0;
        Bundle bundle = new Bundle();
        Constant.start = System.currentTimeMillis();
        for (long i = 0; i < Constant.TEST_TIME; i++) {

        }
        Intent intent = new Intent(MainActivity.this, TestBActivity.class);
        intent.putExtras(bundle);
        Log.i("Zero", "testEmpty: " + intent);
        startActivityForResult(intent, Constant.REQ_CDOE);
    }

    @OnClick(R.id.tvParcelable)
    public void testParcelabel() {
        Constant.MODE = 1;
        Bundle bundle = new Bundle();
        Constant.start = System.currentTimeMillis();
        for (long i = 0; i < Constant.TEST_TIME; i++) {
            bundle.putParcelable("parcelable" + i, student);
        }
        Intent intent = new Intent(MainActivity.this, TestBActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.REQ_CDOE);
    }

    @OnClick(R.id.tvSerializable)
    public void testSerializable() {
        Constant.MODE = 2;
        Bundle bundle = new Bundle();
        Constant.start = System.currentTimeMillis();
        for (long i = 0; i < Constant.TEST_TIME; i++) {
            bundle.putSerializable("Serializable" + i, _student);
        }
        Intent intent = new Intent(MainActivity.this, TestBActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.REQ_CDOE);
    }

    private void initStudent() {
        student = new Student("Lance", "女", 28);
        for (int i = 0; i < Constant.TEST_MOUNT; i++) {
            student.addCourse(new Course("英语", 23.5f));
        }
    }

    private void init_Student() {
        _student = new _Student("King", "男", 38);
        for (int i = 0; i < Constant.TEST_MOUNT; i++) {
            _student.addCourse(new _Course("英语", 83.5f));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            totalTestTime++;
            if (totalTestTime < Constant.TEST_CYCLE) {
                switch (Constant.MODE) {
                    case 0:
                        testEmpty();
                        break;
                    case 1:
                        testParcelabel();
                        break;
                    case 2:
                        testSerializable();
                        break;

                }
            } else {
                switch (Constant.MODE) {
                    case 0:
                        resultEmpty();
                        break;
                    case 1:
                        resultParcelable();
                        break;
                    case 2:
                        resultSerializable();
                        break;

                }

            }

        }

    }

    private void resultEmpty() {
        perEmpty = Constant.perTime();
        totalTestTime = 0;
        tvResult.setText("Empty :" + perEmpty);
    }

    private void resultParcelable() {
        perParcelabel = Constant.perTime();
        totalTestTime = 0;
        tvResultP.setText("parcelable:" + perParcelabel);
    }

    private void resultSerializable() {
        perSerializable = Constant.perTime();
        totalTestTime = 0;
        tvResultS.setText("serializable" + perSerializable);
        float result = (perSerializable - perEmpty) / (perParcelabel - perEmpty);
        tvResult.setText("Result:" + result);
    }

    @OnClick(R.id.btnXml)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, XMLActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnJson)
    public void onViewClicked1() {
        Intent intent = new Intent(MainActivity.this, JSONActivity.class);
        startActivity(intent);
    }
}
