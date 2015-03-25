#include <jni.h>


#ifndef _Included_KeyboardHook
#define _Included_KeyboardHook
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_jbrown_jni_keystroke_BrownKeyboardHook_registerHook(JNIEnv *,jobject thisObj,jobject listenerObj);

JNIEXPORT void JNICALL Java_com_jbrown_jni_keystroke_BrownKeyboardHook_unregisterHook(JNIEnv *env,jobject thisObj);

#ifdef __cplusplus
}
#endif
#endif
