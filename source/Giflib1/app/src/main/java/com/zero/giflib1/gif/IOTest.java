package com.zero.giflib1.gif;

import java.io.FileInputStream;
import java.io.InputStream;

public class IOTest {

    private native void testnative();

    public static void Test() throws Exception {
        InputStream inputStream = new FileInputStream("aaa.txt");
        byte[] buffer = new byte[1024];

        //java IO流的读取
        inputStream.read(buffer,0,buffer.length);
    }
}
