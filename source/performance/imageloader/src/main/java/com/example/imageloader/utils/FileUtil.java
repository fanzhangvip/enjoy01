package com.example.imageloader.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileUtil {
    public static boolean copyStream(InputStream input, OutputStream output)
    {
        byte[] buffer = new byte[1024]; // Adjust if you want
        int bytesRead;
        try {
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }catch (IOException e){
            MLog.e("FileUtil","copyStream Error:" + e.getMessage());
            return false;
        }
        return true;
    }
}
