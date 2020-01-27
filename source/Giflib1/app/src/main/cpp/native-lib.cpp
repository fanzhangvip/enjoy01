#include <jni.h>
#include <string>

#include <gif_lib.h>

#include "GifFrame.h"



extern "C"
JNIEXPORT jobject JNICALL
com_zero_giflib1_gif_GifFrame_nativeDecodeStream(JNIEnv *env, jobject thiz,jobject stream,jbyteArray buffer){

    //类似java的反射 Java 的InputStream jclass
    jclass  inputStreamClazz = env->FindClass("java/io/InputStream");
    JavaInputStream::readMethodId = env->GetMethodID(inputStreamClazz,"read","([BII)I");

    JavaInputStream inputStream(env,stream,buffer);
    //jni层的GifFrame
    GifFrame* gifFrame = new GifFrame(&inputStream);
    //创建java层的GifFrame
    jclass  gifFrameClazz = env->FindClass("com/zero/giflib1/gif/GifFrame");
    // 调用构造方法
    jmethodID gifFrameInitId = env->GetMethodID(gifFrameClazz,"<init>","(JIII)V");

    return env->NewObject(gifFrameClazz,gifFrameInitId,
                          reinterpret_cast<jlong>(gifFrame),
                          gifFrame->getWidth(),
                          gifFrame->getHeight(),
                          gifFrame->getFrameCount());

}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_zero_giflib1_gif_GifFrame_nativeGetFrame(JNIEnv *env, jobject thiz, jlong native_handle,
                                                  jobject bitmap, jint frame_index) {
    // TODO: GifFrame -> 拿出index帧的数据 loadFrame
    //获取C++层的GifFrame
    GifFrame* gifFrame = reinterpret_cast<GifFrame*>(native_handle);
    jlong delayMs = gifFrame->loadFrame(env,bitmap,frame_index);
    return delayMs;
}
extern "C"
JNIEXPORT jobject JNICALL
decode_jni(JNIEnv *env, jobject thiz, jobject assetManager, jstring gifPath){

    const char* filename = env->GetStringUTFChars(gifPath,0);
    GifFrame* gifFrame = new GifFrame(env,assetManager,filename);
    env->ReleaseStringUTFChars(gifPath,filename);
    //调用Java层的GifFrame的构造函数
    //1. Java层的GifFrame的jclass
    jclass gifFrameClazz = env->FindClass("com/zero/giflib1/gif/GifFrame");
    //2  构造方法的jmethdId
    jmethodID gifFrameInit = env->GetMethodID(gifFrameClazz,"<init>","(JIII)V");//方法的签名
    return env->NewObject(gifFrameClazz,gifFrameInit,
                          reinterpret_cast<jlong>(gifFrame)
            ,gifFrame->getWidth()
            ,gifFrame->getHeight()
            ,gifFrame->getFrameCount());
}

JNINativeMethod method[] = {
        {"nativeDecodeStreamJNI"
                ,"(Landroid/content/res/AssetManager;Ljava/lang/String;)Lcom/zero/giflib1/gif/GifFrame;",(void*) decode_jni },//
};

jint registNativeMethod(JNIEnv* env){
    jclass cl = env->FindClass("com/zero/giflib1/gif/GifFrame");
    if(env->RegisterNatives(cl,method,sizeof(method)/ sizeof(method[0])) < 0){
        return -1;
    }
    return 0;
}

jint unRegistNativeMethod(JNIEnv* env){
    jclass cl = env->FindClass("com/zero/giflib1/gif/GifFrame");
    if(env->UnregisterNatives(cl)){
        return -1;
    }
    return 0;
}

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* param){//动态注册
    JNIEnv* env;
    if(vm->GetEnv(reinterpret_cast<void**>(&env),JNI_VERSION_1_6) == JNI_OK){
        registNativeMethod(env);
        return JNI_VERSION_1_6;
    }else if(vm->GetEnv(reinterpret_cast<void**>(&env),JNI_VERSION_1_4) == JNI_OK){
        registNativeMethod(env);
        return JNI_VERSION_1_4;
    }
    return JNI_ERR;

}

JNIEXPORT void JNI_OnUnload(JavaVM* vm, void* param){
    JNIEnv* env;
    if(vm->GetEnv(reinterpret_cast<void**>(&env),JNI_VERSION_1_6) == JNI_OK){
        unRegistNativeMethod(env);
    }else if(vm->GetEnv(reinterpret_cast<void**>(&env),JNI_VERSION_1_4) == JNI_OK){
        unRegistNativeMethod(env);
    }
}
