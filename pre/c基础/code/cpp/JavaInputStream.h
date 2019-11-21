

#ifndef GIFFRAME_JAVAINPUTSTREAM_H
#define GIFFRAME_JAVAINPUTSTREAM_H

#include <jni.h>

class JavaInputStream {
public:
    JavaInputStream(JNIEnv* env, jobject inputStream, jbyteArray byteArray);

public:
    size_t read(void* buffer, size_t size);
private:
    JNIEnv* mEnv;
    const jobject mInputStream;
    const jbyteArray mByteArray;
    const size_t mByteArrayLength;

public:
    static jmethodID  readMethodId;

};


#endif //GIFFRAME_JAVAINPUTSTREAM_H
