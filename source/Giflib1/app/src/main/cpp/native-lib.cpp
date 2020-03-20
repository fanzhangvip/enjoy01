#include <jni.h>
#include <string>

#include <gif_lib.h>

#include "GifFrame.h"




extern "C"
JNIEXPORT jobject JNICALL
com_zero_giflib1_gif_GifFrame_nativeDecodeStream(JNIEnv *env, jobject thiz,jobject stream,jbyteArray buffer){

}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_zero_giflib1_gif_GifFrame_nativeGetFrame(JNIEnv *env, jclass thiz, jlong native_handle,
                                                  jobject bitmap, jint frame_index) {
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