package com.zero.xuliehuademo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.zero.xuliehuademo.parcelable.Student;
import com.zero.xuliehuademo.serializable._Student;

public class TestBActivity extends AppCompatActivity {

    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        bundle = getIntent().getExtras();
        Log.i("Zero","TestBActivity: " + bundle);
        switch (Constant.MODE){
            case 0:
                testEmpty();
                break;
            case 1:
                testParcelable();
                break;
            case 2:
                testSerializable();
                break;

        }
        Constant.result();
        setResult(RESULT_OK);
        finish();
    }

    private void testEmpty(){
        Constant.end = System.currentTimeMillis();
        Log.i("Zero","Constant.end: " + Constant.end);
    }

    private void testParcelable(){
        Student student;
        for(int i = 0;i<Constant.TEST_TIME;i++){
            student = bundle.getParcelable("parcelable" + i);
            Log.i("Zero","student: " + student);
        }
        Constant.end = System.currentTimeMillis();
    }

    private void testSerializable(){
        _Student _student;
        for(int i = 0;i<Constant.TEST_TIME;i++){
            _student = (_Student) bundle.getSerializable("Serializable" + i);
            Log.i("Zero","_student: " + _student);
        }
        Constant.end = System.currentTimeMillis();
    }
}
