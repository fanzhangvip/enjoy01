#include <jni.h>
#include <string>

#include <gif_lib.h>

#include "GifFrame.h"




extern "C"
JNIEXPORT jobject JNICALL
Java_com_zero_giflib1_gif_GifFrame_nativeDecodeStream(JNIEnv *env, jclass thiz,jobject stream,jbyteArray buffer){

    //创建Java的InputStream的read
    //1. 获取InputStream的类
    jclass  inputStreamClazz = env->FindClass("java/io/InputStream");
    //2. 找到方法id
    JavaInputStream::readMethodId = env->GetMethodID(inputStreamClazz,"read","([BII)I");
    JavaInputStream inputStream(env,stream,buffer);
    //创建一个c++层的GifFrame
    GifFrame* gifFrame = new GifFrame(&inputStream);
    //创建java层的GifFrame
    jclass gifFrameClazz = env->FindClass("com/zero/giflib1/gif/GifFrame");
    //调用构造函数
    jmethodID  gifFrameInit = env->GetMethodID(gifFrameClazz,"<init>","(JIII)V");
    return env->NewObject(gifFrameClazz,gifFrameInit,
                          reinterpret_cast<jlong>(gifFrame)
                          ,gifFrame->getWidth()
                          ,gifFrame->getHeight()
                          ,gifFrame->getFrameCount());





}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_zero_giflib1_gif_GifFrame_nativeGetFrame(JNIEnv *env, jclass thiz, jlong native_handle,
                                                  jobject bitmap, jint frame_index) {
    GifFrame* gifFrame = reinterpret_cast<GifFrame*>(native_handle);
    gifFrame->loadFrame(env,bitmap,frame_index);

}
extern "C"
JNIEXPORT jobject JNICALL
decode_jni(JNIEnv *env, jclass thiz, jobject assetManager, jstring gifPath){

}




JNINativeMethod method[] = {
        {"nativeDecodeStreamJNI"
                ,"(Landroid/content/res/AssetManager;Ljava/lang/String;)Lcom/zero/giflib1/gif/GifFrame;"
                ,(void*) decode_jni },//
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

extern "C"
JNIEXPORT jobject JNICALL
Java_com_zero_giflib1_gif_GifFrame_nativeDecodeStream(JNIEnv *env, jclass clazz, jobject stream,
                                                      jbyteArray buffer) {


}