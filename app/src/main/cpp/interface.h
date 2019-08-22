#ifndef INTERFACE_H
#define INTERFACE_H

#include <android/log.h>

#define  LOG_TAG    "f5c_native_log"

#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
// If you want you can add other log definition for info, warning etc


int init(int argc, char* argv[]);

#endif