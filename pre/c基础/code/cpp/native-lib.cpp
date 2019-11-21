#include <jni.h>
#include <string>
extern "C"{
#include "GifFrame.h"
}

jclass gifFrameClazz;
jmethodID gifFrameInit;

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }
    jclass clazz = env->FindClass("com/zero/gifframe/GifFrame");
    gifFrameClazz = static_cast<jclass>(env->NewGlobalRef(clazz));
    gifFrameInit = env->GetMethodID(gifFrameClazz, "<init>", "(JIII)V");


    jclass inputStreamClazz = env->FindClass("java/io/InputStream");
    JavaInputStream::readMethodId = env->GetMethodID(inputStreamClazz, "read", "([BII)I");
    return JNI_VERSION_1_6;
}


static jobject createJavaGIf(JNIEnv *env, GifFrame *frame) {
    if (!frame) {
        return NULL;
    }
    return env->NewObject(gifFrameClazz, gifFrameInit,
                          reinterpret_cast<jlong>(frame),
                          frame->getWidth(),
                          frame->getHeight(),
                          frame->getFrameCount());
}


extern "C"
JNIEXPORT jobject JNICALL
Java_com_zero_gifframe_GifFrame_nativeDecodeStream(JNIEnv *env, jclass type, jobject jstream,
                                                      jbyteArray byteArray) {
    JavaInputStream stream(env, jstream, byteArray);
    GifFrame *gifFrame = new GifFrame(&stream);
    return createJavaGIf(env, gifFrame);
}



extern "C"
JNIEXPORT jlong JNICALL
Java_com_zero_gifframe_GifFrame_nativeGetFrame(JNIEnv *env, jobject instance, jlong nativeHandle,
                                                  jobject bitmap, jint frameIndex) {

    GifFrame *gifFrame = reinterpret_cast<GifFrame *>(nativeHandle);
    jlong delayMs = gifFrame->drawFrame(env, bitmap, frameIndex);
    return delayMs;
}