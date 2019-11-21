package com.zero.gifframe;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.FileObserver;
import android.support.annotation.Nullable;

import java.io.InputStream;


public class GifFrame {


    static {
        System.loadLibrary("native-lib");
    }

    private final long nativeHandle;
    private final int width;
    private final int height;
    private final int frameCount;

    public static GifFrame decodeStream(InputStream stream) {
        byte[] buffer = new byte[16 * 1024];
        return nativeDecodeStream(stream, buffer);
    }

    private static native GifFrame nativeDecodeStream(InputStream stream, byte[] buffer);

    private native long nativeGetFrame(long nativeHandle, Bitmap bitmap, int frameIndex);

    private GifFrame(long nativeHandle, int width, int height, int frameCount) {
        this.nativeHandle = nativeHandle;
        this.width = width;
        this.height = height;
        this.frameCount = frameCount;

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public long getFrame(Bitmap bitmap, int frameIndex) {
        return nativeGetFrame(nativeHandle, bitmap, frameIndex);
    }

}
