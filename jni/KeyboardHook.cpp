#include <windows.h>
#include <jni.h>
#include "KeyboardHook.h"

#ifdef DEBUG
#define DEBUG_PRINT(x) printf x
#else
#define DEBUG_PRINT(x) do {} while (0)
#endif 

HINSTANCE hInst = NULL;

JavaVM * jvm = NULL;
DWORD hookThreadId = 0;

jobject keyboardHookObject = NULL;
jobject globalKeyListenerObject = NULL;
jmethodID processKeyMethod = NULL;

extern "C"
BOOL APIENTRY DllMain(HINSTANCE _hInst,DWORD reason,LPVOID reserved)  {
	switch(reason) {
		case DLL_PROCESS_ATTACH:
			DEBUG_PRINT(("NATIVE: DllMain - DLL_PROCESS_ATTACH.\n"));
			hInst = _hInst;
			break;
		default:
			break;
	}
	return TRUE;
}

LRESULT CALLBACK LowLevelKeyboardProc(int nCode,WPARAM wParam,LPARAM lParam)  {
	JNIEnv* env;
	KBDLLHOOKSTRUCT* p = (KBDLLHOOKSTRUCT *)lParam;
	if(jvm->AttachCurrentThread((void **)&env, NULL)>=0) {
		jboolean transitionState = (jboolean)FALSE;
		switch(wParam)  {
			case WM_KEYDOWN: case WM_SYSKEYDOWN:
				transitionState = (jboolean)TRUE;
			case WM_KEYUP: case WM_SYSKEYUP:
				env->CallVoidMethod(keyboardHookObject,processKeyMethod,transitionState,p->vkCode,globalKeyListenerObject);
				break;
			default:
				break;
		}
	}	else DEBUG_PRINT(("NATIVE: LowLevelKeyboardProc - Error on the attach current thread.\n"));
	return CallNextHookEx(NULL,nCode,wParam,lParam);
}

JNIEXPORT void JNICALL Java_com_jbrown_jni_keystroke_BrownKeyboardHook_registerHook(JNIEnv * env,jobject obj,jobject _globalKeyListenerObject) {
	DEBUG_PRINT(("NATIVE: Java_com_jbrown_jni_keystroke_BrownKeyboardHook_registerHook - Hooking started!\n"));
	
	HHOOK hookHandle = SetWindowsHookEx(WH_KEYBOARD_LL,LowLevelKeyboardProc,hInst,0);
	globalKeyListenerObject = _globalKeyListenerObject;
	
	if(hookHandle==NULL) {
		DEBUG_PRINT(("NATIVE: Java_com_jbrown_jni_keystroke_BrownKeyboardHook_registerHook - Hook failed!\n"));
		return;
	} else DEBUG_PRINT(("NATIVE: Java_com_jbrown_jni_keystroke_BrownKeyboardHook_registerHook - Hook successful\n"));
	
	keyboardHookObject = env->NewGlobalRef(obj);
	jclass cls = env->GetObjectClass(keyboardHookObject);
	processKeyMethod = env->GetMethodID(cls,"processKey","(ZILcom/jbrown/jni/keystroke/GlobalKeyListener;)V");
	
	env->GetJavaVM(&jvm);
	hookThreadId = GetCurrentThreadId();
	
	MSG message;
	while(GetMessage(&message,NULL,0,0)) {
		TranslateMessage(&message);
		DispatchMessage(&message);
	}
	
	DEBUG_PRINT(((!UnhookWindowsHookEx(hookHandle))?("NATIVE: Java_com_jbrown_jni_keystroke_BrownKeyboardHook_registerHook - Unhook failed\n")
	                                                :"NATIVE: Java_com_jbrown_jni_keystroke_BrownKeyboardHook_registerHook - Unhook successful\n"));
}

JNIEXPORT void JNICALL Java_com_jbrown_jni_keystroke_BrownKeyboardHook_unregisterHook(JNIEnv *env,jobject object) {
	if(hookThreadId==0) return;	
	DEBUG_PRINT(("NATIVE: Java_com_jbrown_jni_keystroke_BrownKeyboardHook_unregisterHook - call PostThreadMessage.\n"));
	PostThreadMessage(hookThreadId,WM_QUIT,0,0L);
}
