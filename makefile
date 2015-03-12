# Define a variable for classpath
CLASS_PATH = ../bin

# Define a virtual path for .class in the bin directory
vpath %.class $(CLASS_PATH)

all : clean KeyboardHook.dll


 # del or rm for window/unix
clean :
	del  KeyboardHook.o KeyboardHook.dll
	
#KeyboardHook.h : MouseHook.class
#	javah -classpath .;$(CLASS_PATH);bin\ $*	


# KeyboardHook.o : jni/KeyboardHook.cpp jni/KeyboardHook.h
#	g++ -I"$(JAVA_HOME)include" -I"$(JAVA_HOME)include\win32" -c $< -o $@  


KeyboardHook.o : jni/KeyboardHook.cpp jni/KeyboardHook.h
	 g++ -I"$(JAVA_HOME)include" -I"$(JAVA_HOME)include\win32" -g3 -Wall -fmessage-length=0 -D_JNI_IMPLEMENTATION_ -c $< -o $@ 

KeyboardHook.dll : KeyboardHook.o
#	g++ -Wl,--add-stdcall-alias -shared -o $@ $<
	g++ -Wl,-kill-at -shared -static-libgcc -shared -o $@ $<
	

# g++ -Wl,–-kill-at -shared -static-libgcc -shared -o $@ $<
# -Wl,–kill-at -mno-cygwin -shared -static-libgcc -shared


#TestHookJNI.h : TestHookJNI.class
#	javah -jni -classpath $(CLASS_PATH) $*	

#TestHookJNI.o : TestHookJNI.cpp TestHookJNI.h
#	g++ -I"$(JAVA_HOME)include" -I"$(JAVA_HOME)include\win32" -c $< -o $@  

#TestHookJNI.dll : TestHookJNI.o
#	g++ -Wl,--add-stdcall-alias -shared -o $@ $<	