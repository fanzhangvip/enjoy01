package com.enjoy.zero.javapoetdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.enjoy.zero.annotations.BindView;
import com.enjoy.zero.annotations.MyAnnotation;
import com.enjoy.zero.api.MyButterknife;
import com.squareup.picasso.Picasso;


@MyAnnotation("Hello World")
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.test)
    TextView mTextView;

    @BindView(R.id.button)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyButterknife.bind(this);
        mTextView.setText("hahahahahah.....");
        ImageView imageView;
        Picasso.with(this).load(R.drawable.ic_launcher_foreground).fit();


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyButterknife.unBind(this);
    }
}
