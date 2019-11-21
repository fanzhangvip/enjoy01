package com.zero.gifframe;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class GifDrawable extends Drawable implements Animatable, Runnable {

    private final GifFrame gifFrame;
    private final BitmapProvider bitmapProvider;
    private final Rect srcRect;
    private Bitmap bitmap;
    private final Paint paint;
    private final int frameCount;
    private int frameIndex;
    private boolean isRunning;
    long showtime, curTime;

    public interface BitmapProvider {
        Bitmap acquireBitmap(int minWidth, int minHeight);

        void releaseBitmap(Bitmap bitmap);
    }

    public GifDrawable(GifFrame gifFrame, BitmapProvider bitmapProvider) {
        this.gifFrame = gifFrame;
        this.bitmapProvider = bitmapProvider;

        int width = gifFrame.getWidth();
        int height = gifFrame.getHeight();
        frameCount = gifFrame.getFrameCount();
        frameIndex = 0;

        bitmap = bitmapProvider.acquireBitmap(width, height);
        paint = new Paint();
        paint.setFilterBitmap(true);
        gifFrame.getFrame(bitmap, getFrameIndex());
        srcRect = new Rect(0, 0, width, height);
    }

    @Override
    public void start() {
        if (!isRunning()) {
            isRunning = true;
            scheduleSelf(this, SystemClock.uptimeMillis());
        }
    }

    @Override
    public void stop() {
        if (isRunning()) {
            isRunning = false;
            unscheduleSelf(this);
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, getBounds(), paint);
        showtime = SystemClock.uptimeMillis();
        if (isRunning()) {
            scheduleSelf(this, showtime + curTime);
        }
    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public void run() {
        curTime = gifFrame.getFrame(bitmap, getFrameIndex());
        invalidateSelf();
    }

    public int getFrameIndex() {
        frameIndex++;
        return frameIndex < frameCount ? frameIndex : (frameIndex = 0);
    }
}
