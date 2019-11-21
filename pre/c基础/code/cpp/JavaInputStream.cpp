

#include "JavaInputStream.h"


#define min(a, b) \
    a<b?a:b


jmethodID JavaInputStream::readMethodId = 0;

JavaInputStream::JavaInputStream(JNIEnv *env, jobject inputStream, jbyteArray byteArray) :
        mEnv(env),
        mInputStream(inputStream),
        mByteArray(byteArray),
        mByteArrayLength(env->GetArrayLength(byteArray)) {
}


size_t JavaInputStream::read(void *buffer, size_t size) {
    //读取的总数据大小
    size_t totalBytesRead = 0;
    //读取数据时 可能一次并不能读满想要的 size,所以循环读取
    do {
        //获得较小的数
        size_t requested = min(size, mByteArrayLength);
        jint bytesRead = mEnv->CallIntMethod(mInputStream,
                                             readMethodId, mByteArray, 0, requested);
        //如果出现异常或者未读取到数据
        if (mEnv->ExceptionCheck() || bytesRead < 0) {
            return 0;
        }
        //将读取的数据放入buffer中
        mEnv->GetByteArrayRegion(mByteArray, 0, bytesRead, static_cast<jbyte *>(buffer));
        //下次读取从buffer的 bytesRead 处开始保存
        buffer = (char*)buffer+ bytesRead;
        //同时还需要读取的
        size -= bytesRead;
        //总读取大小
        totalBytesRead += bytesRead;
    } while (size > 0);
    return totalBytesRead;
}


