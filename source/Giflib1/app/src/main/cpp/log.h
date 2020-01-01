//
// Created by Zero on 2019/11/17.
//

#ifndef GIFLIBDEMO_LOG_H
#define GIFLIBDEMO_LOG_H
#define LOG_DEBUG true
#define TAG "Zero"

#include <android/log.h>

#ifdef LOG_DEBUG
#define LOGI(...) \
        __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#else
#define LOGI(...)
#endif
#endif //GIFLIBDEMO_LOG_H
