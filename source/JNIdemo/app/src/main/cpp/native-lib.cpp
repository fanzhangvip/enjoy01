#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
JNICALL
Java_com_zero_jnidemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_zero_lib_JNITest_test(JNIEnv *env, jobject thiz) {
    // TODO: implement test()
}