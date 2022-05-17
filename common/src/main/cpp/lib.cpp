#include <jni.h>
#include <string>
extern "C"
JNIEXPORT jstring JNICALL
Java_com_app_common_Secrets_githubToken(JNIEnv *env, jobject thiz) {
    std::string api_key = "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";
    return env->NewStringUTF(api_key.c_str());
}