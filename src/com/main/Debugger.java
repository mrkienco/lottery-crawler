package com.main;

public class Debugger {
	private boolean debug = false;
	private static Debugger INSTANCE;

	private Debugger() {
	}

	public static Debugger get() {
		if (INSTANCE == null) {
			INSTANCE = new Debugger();
		}
		return INSTANCE;
	}

	public void log(String str) {
		if (this.debug)
			System.out.println(str);
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
