#include <jni.h>
#include <string>
#include <malloc.h>
#include <android/log.h>

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR  , "pjwdemoC", __VA_ARGS__)

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_pengjunwei_android_custom_demo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_pengjunwei_android_custom_demo_util_NativeTool_eatMemory(JNIEnv *env, jobject instance,
                                                                  jlong size) {

//    size_t tempSize = static_cast<size_t>(size);
    long tempSize = size - 100*1024;
    void *ptr = malloc(tempSize);
    LOGE("pjwfocus tempSize is %d size is %d, ptr is %d ",tempSize,size,ptr);

    return size;
}