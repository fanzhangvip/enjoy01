package com.example.handlerdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.handlerdemo.myhandler.MyHandler;
import com.example.handlerdemo.myhandler.MyLooper;
import com.example.handlerdemo.myhandler.MyMessage;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HandlerDemo";

    private static final int FLASH_UI = 1 << 0;

    private Handler mHandler = null;

    private Handler myThreadHandler = null;
    private MyThread myThread = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate 当前线程： " + Thread.currentThread().getName());
        setContentView(R.layout.activity_handler_demo);

        mHandler = new Handler();

    }

    public void handlerUsageOne(View view) {
        //handler用法一
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                Log.i(TAG, "handler用法1 Runnable.run 当前线程： " + Thread.currentThread().getName());
            }
        });
    }

    public void handlerUsageTwo(View view) {

        Log.i(TAG, "handler用法2 handlerUsageTwo ： " + Thread.currentThread().getName());
        final MyHandler1 myHandler1 = new MyHandler1();
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "handler用法2 Runnable.run 当前线程： " + Thread.currentThread().getName());
                Message msg = myHandler1.obtainMessage(FLASH_UI);
                myHandler1.sendMessage(msg);
            }
        };


    }

    public void handlerUsageThree(View view) {

        myThread = new MyThread();
        myThread.start();

    }

    public void handlerUsageFour(View view){
        customHandlerTest();
    }

    class MyThread extends Thread {
        public void run() {
            Log.d(TAG, "Thread[{0}]-- run:" + Thread.currentThread().getName()); // 其它线程中新建一个handler
            Looper.prepare();
            myThreadHandler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    Log.d(TAG, "Thread[{0}]--myThreadHandler handleMessage run..." + Thread.currentThread().getName());
                }
            };
            myThreadHandler.sendEmptyMessage(0);
            Looper.myLooper().loop();//建立一个消息循环，该线程不会退出
        }
    }

    class MyHandler1 extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FLASH_UI:
                    Log.i(TAG, "handler用法2 handleMessage： " + Thread.currentThread().getName());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private void customHandlerTest() {

        new Thread("MyHandler 演示主线程"){
            public void run(){
                MyLooper.prepare();
                final MyHandler handler = new MyHandler() {
                    @Override
                    public void handleMessage(MyMessage message) {
                        Log.i(TAG, Thread.currentThread().getName() +" 接收到的消息 " + message.obj.toString());
                    }
                };
                for (int i = 0; i < 5; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyMessage msg = new MyMessage();
                            synchronized (UUID.class) {
                                msg.obj = UUID.randomUUID().toString();
                            }
                            Log.i(TAG, Thread.currentThread().getName() + ": 发送的消息: " + msg.obj);
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
                MyLooper.loop();
            }
        }.start();


    }
}
