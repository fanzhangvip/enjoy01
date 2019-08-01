package com.enjoy.socketdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.enjoy.socketdemo.utils.NetworkUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Zero";

    @BindView(R.id.tv_ip)
    TextView tvIp;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.et_send)
    EditText etSend;


    //定义相关变量,完成初始化
    private static final String HOST = "192.168.0.185";
    private static final int PORT = 12345;

    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";
    private StringBuilder sb = null;

    private boolean writerFlag = false;

    private LinkedBlockingQueue<String> msgs = new LinkedBlockingQueue<>();

    //定义一个handler对象,用来刷新界面
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                sb.append(content);
                tvShow.setText(sb.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tvIp.setText(NetworkUtils.getIPAddress(this));

        sb = new StringBuilder();
        //当程序一开始运行的时候就实例化Socket对象,与服务端进行连接,获取输入输出流
        //因为4.0以后不能再主线程中进行网络操作,所以需要另外开辟一个线程
        new Thread() {

            public void run() {
                try {
                    socket = new Socket(HOST, PORT);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                            socket.getOutputStream())), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(socketRunnable).start();
    }

    private Runnable socketRunnable = new Runnable() {
        //重写run方法,在该方法中输入流的读取
        @Override
        public void run() {
            try {
                while (true) {
                    if (socket.isConnected()) {
                        if (!socket.isInputShutdown()) {
                            if ((content = in.readLine()) != null) {
                                content += "\n";
                                handler.sendEmptyMessage(0x123);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable socketWriteRunnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                if (socket.isConnected()) {
                    if (!socket.isOutputShutdown()) {
                        try {
                            String msg = msgs.take();
                            out.println(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
                Log.i(TAG, "run: ");
            }
        }
    };


    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        if(!writerFlag){
            new Thread(socketWriteRunnable).start();
        }
        writerFlag = true;

        String msg = etSend.getText().toString();
        try {
            msgs.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
