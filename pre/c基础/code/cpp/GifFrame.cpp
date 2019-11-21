
#include "GifFrame.h"
#include <android/bitmap.h>

#define ARGB_TO_COLOR8888(a, r, g, b) \
    ((a) << 24 | (b) << 16 | (g) << 8 | (r))

static int streamReader(GifFileType *fileType, GifByteType *out, int size) {
    JavaInputStream *stream = (JavaInputStream *) fileType->UserData;
    return (int) stream->read(out, size);
}

static long getDelayMs(GraphicsControlBlock &gcb) {
    return gcb.DelayTime * 10;
}

GifFrame::GifFrame(JavaInputStream *stream) {
    //用户数据 当需要读取更多数据时候会调用 streamReader 函数
    mGif = DGifOpen(stream, streamReader, NULL);
    //初始化结构体
    DGifSlurp(mGif);
    GraphicsControlBlock gcb;
    long durationMs = 0;
    for (int i = 0; i < mGif->ImageCount; i++) {
        //获得显示时间
        DGifSavedExtensionToGCB(mGif, i, &gcb);
        durationMs += getDelayMs(gcb);
    }
}

int GifFrame::getWidth() {
    return mGif ? mGif->SWidth : 0;
}

int GifFrame::getHeight() {
    return mGif ? mGif->SHeight : 0;
}

int GifFrame::getFrameCount() {
    return mGif ? mGif->ImageCount : 0;
}

long GifFrame::drawFrame(JNIEnv *env, jobject bitmap, int frameIndex) {
    AndroidBitmapInfo info;
    uint32_t *pixels;
    AndroidBitmap_getInfo(env, bitmap, &info);
    AndroidBitmap_lockPixels(env, bitmap, reinterpret_cast<void **>(&pixels));
    SavedImage savedImages = mGif->SavedImages[frameIndex];
    GifImageDesc imageDesc = savedImages.ImageDesc;
    ColorMapObject *colorMapObject = mGif->SColorMap ? mGif->SColorMap : imageDesc.ColorMap;
    for (int i = 0; i < imageDesc.Height; ++i) {
        for (int j = 0; j < imageDesc.Width; ++j) {
            int index = i * imageDesc.Width + j;
            //得到的是压缩数据
            GifByteType gifByteType = savedImages.RasterBits[index];
            GifColorType gifColorType = colorMapObject->Colors[gifByteType];
            uint32_t color = ARGB_TO_COLOR8888(0xff, gifColorType.Red, gifColorType.Green,
                                               gifColorType.Blue);
            pixels[index] =  color;
        }
    }
    AndroidBitmap_unlockPixels(env,bitmap);
    GraphicsControlBlock gcb;
    DGifSavedExtensionToGCB(mGif, frameIndex, &gcb);
    return gcb.DelayTime * 10;
}

