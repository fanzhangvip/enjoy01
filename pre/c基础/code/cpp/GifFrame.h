
#ifndef GIFFRAME_GIFFRAME_H
#define GIFFRAME_GIFFRAME_H


#include <gif_lib.h>
#include "JavaInputStream.h"

class GifFrame {
public:
    GifFrame(JavaInputStream *stream);

    ~GifFrame();

    int getWidth();

    int getHeight();

    int getFrameCount();

    long drawFrame(JNIEnv *env, jobject bitmap,int frameIndex);

private:
    GifFileType *mGif;
};


#endif //GIFFRAME_GIFFRAME_H
