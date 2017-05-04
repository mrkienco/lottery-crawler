package com.log;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author chaulh
 */

public class Debug {

	private static LogWriter logWriter;
	private static String moduleName = "log";
	public static boolean isShowConsole = false;
	public static boolean isShutdown = false;

	public static void game(String playerName, String message) {
		if (logWriter == null) {
			initLog();
		}
		String messageFormat = message.replaceAll("@", "*");
		logWriter.writeLog(
				String.format("@%s@game@ %s", playerName, messageFormat),
				LogDef.LOG_LEVEL.LOG_INFO);

	}

	public static void error(String roomName, Exception ex) {
		if (logWriter == null) {
			initLog();
		}

		logWriter.writeLog(
				String.format("@error@ %s --->   %s", roomName, ex.toString()),
				ex, LogDef.LOG_LEVEL.LOG_INFO);
	}

	public static void lobby(String message) {
		if (logWriter == null) {
			initLog();
		}
		String messageFormat = message.replaceAll("@", "*");
		logWriter.writeLog(String.format("@lobby@ %s", messageFormat),
				LogDef.LOG_LEVEL.LOG_INFO);
	}

	public static void d(String message) {
		if (logWriter == null) {
			initLog();
		}
//		console(message);
		String messageFormat = message.replaceAll("@", "*");
		logWriter.writeLog(String.format("@log@ %s", messageFormat),
				LogDef.LOG_LEVEL.LOG_INFO);
	}
	
	public static void money(String message) {
		if (logWriter == null) {
			initLog();
		}
		console(message);
		String messageFormat = message.replaceAll("@", "*");
		logWriter.writeLog(String.format("@money@ %s", messageFormat),
				LogDef.LOG_LEVEL.LOG_INFO);
	}

	public static void toupCard(String message) {
		if (logWriter == null) {
			initLog();
		}
		String messageFormat = message.replaceAll("@", "*");
		logWriter.writeLog(String.format("@toup@ %s", messageFormat),
				LogDef.LOG_LEVEL.LOG_INFO);
	}

	public static void request(String roomName, String message) {
		message.replaceAll("@", "*");
		if (logWriter == null) {
			initLog();
		}
		logWriter.writeLog(String.format("@%s@request@ %s", roomName, message),
				LogDef.LOG_LEVEL.LOG_INFO);
	}

	private static void initLog() {
		logWriter = new LogWriter(moduleName);
		logWriter.setConsoleInfo(true);
		logWriter.open();
	}

	public static void setModuleName(String name) {
		moduleName = name;
	}

	public static void console(String message) {
		System.out.println(message);
	}
}
