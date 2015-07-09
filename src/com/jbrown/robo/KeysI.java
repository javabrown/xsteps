package com.jbrown.robo;

public interface KeysI {
	String APP_LAUNCHER_K = "LAUNCHER";
	String APP_TEST_MODULE_K = "MODULE";
	String APP_LAUNCHER_CLASS_K = "com.jbrown.Main";
	String APP_TEST_MODULE_CLASS_K = "com.jbrown.core.events.EventModuleTester";
	
	String COMMAND_RECORD_K = "Record";
	String COMMAND_STOP_RECORDING_K = "Stop Record";
	String COMMAND_SAVE_K = "Save";
	String COMMAND_REPEAT_K = "Repeat";
	String COMMAND_VERIFY_K = "Verify";
	String COMMAND_RESET_K = "Reset";
	String COMMAND_SMART_WORKER_POWER_SAVE_MODE_K =  "Power-Save Mode";
	String COMMAND_EXIT_K = "Exit";
	
	String SHORTCUT_RECORD_K = "R";
	String SHORTCUT_STOP_RECORDING_K = "K";
	String SHORTCUT_SAVE_K = "S";
	String SHORTCUT_REPEAT_K = "P";
	String SHORTCUT_RESET_K = "E";
	
	String CHECK_FAST_FORWARD_K = "Fast Forward";
	
	String CAPTION_STOP_RECORDING_K = "Stop Recording";
	
	int[] RECORDING_PAUSE_KEY_COMBINATION = new int[]{ 112 };  //112 = F1 code
	
	int RECORDING_RESUME_PAUSE_COMMAND_KEY = 27;
	 
	String RECORDING_PAUSED_LABEL_MSG = "<html>Press <FONT COLOR=BLUE>Esc</FONT> to resume recording</html>";
	
	
	char[][] CHAR_REPLACEMENT_MATRIX = new char[][]{
			{',', ';'},
			{',', '.'},
			{',', '~'},
			
			{'-', ':'},
			{'-', '~'},
			
			{'.', ','},
			{'.', ';'},
			{'.', '~'},
			
			{'!', '.'},
			{'!', ';'},
			{'!', ','},
			
			{';', '.'},
			{';', '|'},
			{';', ','},
			{';', '~'},
	};
	
	
}