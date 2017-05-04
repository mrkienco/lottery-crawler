package com.log;

/*     */
/*     */import java.io.File;
/*     */
import java.io.IOException;
/*     */
import org.apache.log4j.Appender;
/*     */
import org.apache.log4j.BasicConfigurator;
/*     */
import org.apache.log4j.Level;
/*     */
import org.apache.log4j.Logger;
/*     */
import org.apache.log4j.SimpleLayout;
/*     */
import org.apache.log4j.net.SyslogAppender;
/*     */
import org.apache.log4j.spi.LoggerRepository;

/*     */
/*     */public class LogWriter
/*     */{
	/* 23 */boolean bLogToFile = true;
	/* 24 */boolean bLogToConsole = false;
	/* 25 */boolean bLogToSysLog = false;
	/*     */
	/* 27 */public String csLogPath = null;
	/* 28 */public String csModuleName = null;
	/* 29 */int nChangeLogFlag = 3;
	/* 30 */protected int nMaxFileSize = 10485760;
	/* 31 */protected int nMaxFileIndex = LogDef.FILE.MAX_FILE_BACKUP_INDEX;
	/* 32 */protected boolean bFlushImmediately = true;
	/* 33 */static byte nGlobalSystemLogLevel = 0;
	/* 34 */static boolean bConfigured = false;
	/* 35 */public Appender myAppender = null;
	/* 36 */public Appender consoleAppender = null;
	/* 37 */protected static String csDateTime = null;
	/*     */
	/* 39 */Appender[] arrSyslogAppender = null;
	/* 40 */private String loggerName = "";
	/* 41 */private String syslogHost = "";
	/* 42 */private int nSyslogPort = 513;
	/* 43 */private static final Logger loggerObj = Logger
			.getLogger(LogWriter.class);
	/*     */private boolean isShowConsole;

	/*     */
	/*     */public LogWriter(String loggerName)
	/*     */{
		/* 47 */this.loggerName = loggerName;
		/* 48 */loggerObj.setAdditivity(false);
		/*     */}

	/*     */
	/*     */public void setIsShowConsole(boolean isShowConsole) {
		/* 52 */this.isShowConsole = isShowConsole;
		/*     */}

	/*     */
	/*     */public void setLogLevel(byte level) {
		/* 56 */if (loggerObj == null) {
			/* 57 */return;
			/*     */}
		/*     */
		/* 60 */switch (level) {
		/*     */case 0:
			/* 62 */loggerObj.setLevel(Level.ALL);
			/* 63 */break;
		/*     */case 1:
			/* 65 */loggerObj.setLevel(Level.DEBUG);
			/* 66 */break;
		/*     */case 2:
			/* 68 */loggerObj.setLevel(Level.INFO);
			/* 69 */break;
		/*     */case 3:
			/* 71 */loggerObj.setLevel(Level.WARN);
			/* 72 */break;
		/*     */case 4:
			/* 74 */loggerObj.setLevel(Level.ERROR);
			/* 75 */break;
		/*     */case 5:
			/* 77 */loggerObj.setLevel(Level.FATAL);
			/* 78 */break;
		/*     */case 6:
			/* 80 */loggerObj.setLevel(Level.OFF);
			/* 81 */break;
		/*     */default:
			/* 83 */loggerObj.setLevel(Level.ERROR);
			/*     */}
		/*     */}

	/*     */
	/*     */protected static Level getLevelObject(byte level)
	/*     */{
		/* 91 */Level levelObj = null;
		/* 92 */switch (level) {
		/*     */case 0:
			/* 94 */levelObj = Level.ALL;
			/* 95 */break;
		/*     */case 1:
			/* 97 */levelObj = Level.DEBUG;
			/* 98 */break;
		/*     */case 2:
			/* 100 */levelObj = Level.INFO;
			/* 101 */break;
		/*     */case 3:
			/* 103 */levelObj = Level.WARN;
			/* 104 */break;
		/*     */case 4:
			/* 106 */levelObj = Level.ERROR;
			/* 107 */break;
		/*     */case 5:
			/* 109 */levelObj = Level.FATAL;
			/* 110 */break;
		/*     */case 6:
			/* 112 */levelObj = Level.OFF;
			/* 113 */break;
		/*     */}
		/*     */
		/* 118 */return levelObj;
		/*     */}

	/*     */
	/*     */protected boolean IsReady() {
		/* 122 */return loggerObj != null;
		/*     */}

	/*     */
	/*     */protected void createFileAppender(String filename,
			byte nChangeFileRule, int nMaxSize, int nMaxIndex)
	/*     */{
		/* 138 */loggerObj.setLevel(Level.INFO);
		/* 139 */loggerObj.removeAllAppenders();
		/*     */try
		/*     */{
			/* 146 */this.myAppender = new MyAppender(new SimpleLayout(),
					"fileName");
			/*     */}
		/*     */catch (IOException localIOException)
		/*     */{
			/*     */}
		/* 151 */loggerObj.addAppender(this.myAppender);
		/*     */}

	/*     */
	/*     */protected void createConsoleAppender()
	/*     */{
		/* 158 */loggerObj.setAdditivity(false);
		/*     */}

	/*     */
	/*     */public void createSyslogAppender(String syslogAddr, int nPort,
			int syslogIndex)
	/*     */{
		/* 182 */if (this.arrSyslogAppender == null) {
			/* 183 */this.arrSyslogAppender = new Appender[syslogIndex + 1];
			/*     */}
		/* 185 */if (syslogIndex >= this.arrSyslogAppender.length)
		/*     */{
			/* 187 */Appender[] newAppenderArr = new Appender[syslogIndex + 1];
			/* 188 */for (int oldSyslogIdx = 0; oldSyslogIdx < this.arrSyslogAppender.length; oldSyslogIdx++) {
				/* 189 */newAppenderArr[oldSyslogIdx] = this.arrSyslogAppender[oldSyslogIdx];
				/* 190 */this.arrSyslogAppender[oldSyslogIdx] = null;
				/*     */}
			/* 192 */this.arrSyslogAppender = newAppenderArr;
			/*     */}
		/*     */
		/* 195 */if (this.arrSyslogAppender[syslogIndex] != null) {
			/* 196 */loggerObj
					.removeAppender(this.arrSyslogAppender[syslogIndex]);
			/*     */}
		/*     */
		/* 199 */this.arrSyslogAppender[syslogIndex] =
		/* 200 */new SyslogAppender(new SimpleLayout(),
		/* 200 */40);
		/* 201 */((SyslogAppender) this.arrSyslogAppender[syslogIndex])
		/* 202 */.setSyslogHost(syslogAddr);
		/* 203 */loggerObj.addAppender(this.arrSyslogAppender[syslogIndex]);
		/*     */}

	/*     */
	public void writeLog(String logMessage, byte nLevel) {
		csDateTime = DateFormat.formatTime24();
		logMessage = csDateTime + " INFO   " + logMessage;
		loggerObj.info(logMessage);
		if (Debug.isShowConsole) {
			Debug.console(logMessage);
		}
	}

	/*     */
	/*     */public void writeLog(String logMessage, Exception ex, byte nLevel) {
		/* 224 */csDateTime = DateFormat.formatTime24();
		/* 225 */logMessage = csDateTime + " INFO   " + logMessage;
		/*     */
		/* 228 */loggerObj.error(logMessage, ex);
		/*     */}

	/*     */
	/*     */public static void setGlobalLogLevel(byte level) {
		/* 232 */if (!bConfigured) {
			/* 233 */bConfigured = true;
			/* 234 */BasicConfigurator.configure();
			/*     */}
		/* 236 */Logger logger = Logger.getLogger("com.elcom.smsroaming");
		/* 237 */LoggerRepository repository = logger.getLoggerRepository();
		/* 238 */repository.setThreshold(getLevelObject(level));
		/*     */}

	/*     */
	/*     */public static void resetLogDefucation() {
		/* 242 */BasicConfigurator.resetConfiguration();
		/*     */}

	/*     */
	/*     */protected void removeFileAppender()
	/*     */{
		/* 249 */if (this.myAppender != null)
			/* 250 */loggerObj.removeAppender(this.myAppender);
		/*     */}

	/*     */
	/*     */public void removeConsoleAppender()
	/*     */{
		/* 258 */if (this.consoleAppender != null)
			/* 259 */loggerObj.removeAppender(this.consoleAppender);
		/*     */}

	/*     */
	/*     */public void removeSyslogAppender()
	/*     */{
		/* 267 */if (this.arrSyslogAppender != null)
			/* 268 */for (int i = 0; i < this.arrSyslogAppender.length; i++)
				/* 269 */if (this.arrSyslogAppender[i] != null)
					/* 270 */loggerObj
							.removeAppender(this.arrSyslogAppender[i]);
		/*     */}

	/*     */
	/*     */public void setFileLogInfo(boolean bActive, String csPath,
			String filename, byte nChangeFileFlag, int nMaxSize, int nMaxIndex,
			boolean flushImmediately)
	/*     */{
		/* 293 */File logdir = new File(csPath);
		/* 294 */if (!logdir.exists()) {
			/* 295 */logdir.mkdirs();
			/*     */}
		/* 297 */this.csLogPath = csPath;
		/* 298 */this.csModuleName = filename;
		/*     */
		/* 301 */this.nChangeLogFlag = 3;
		/*     */
		/* 303 */this.nMaxFileSize = 2097152;
		/*     */
		/* 305 */this.nMaxFileIndex = 2000;
		/* 306 */this.bFlushImmediately = flushImmediately;
		/*     */
		/* 308 */this.bLogToFile = bActive;
		/*     */}

	/*     */
	/*     */public void setSysLogInfo(boolean bActive, String syslogHost, int nPort)
	/*     */{
		/* 321 */this.syslogHost = syslogHost;
		/* 322 */this.nSyslogPort = nPort;
		/* 323 */this.bLogToSysLog = bActive;
		/*     */}

	/*     */
	/*     */public void setConsoleInfo(boolean bActive)
	/*     */{
		/* 332 */this.bLogToConsole = bActive;
		/*     */}

	/*     */
	/*     */public void close()
	/*     */{
		/* 339 */if (this.myAppender != null) {
			/* 340 */this.myAppender.close();
			/* 341 */this.myAppender = null;
			/*     */}
		/*     */
		/* 344 */if (this.arrSyslogAppender != null) {
			/* 345 */for (int i = 0; i < this.arrSyslogAppender.length; i++) {
				/* 346 */if (this.arrSyslogAppender[i] != null) {
					/* 347 */this.arrSyslogAppender[i].clearFilters();
					/* 348 */this.arrSyslogAppender[i].close();
					/* 349 */this.arrSyslogAppender[i] = null;
					/*     */}
				/*     */}
			/*     */}
		/*     */
		/* 354 */if (this.consoleAppender != null) {
			/* 355 */this.consoleAppender.close();
			/* 356 */this.consoleAppender = null;
			/*     */}
		/*     */
		/* 359 */loggerObj.removeAllAppenders();
		/*     */}

	/*     */
	/*     */public void open()
	/*     */{
		/* 367 */if (this.bLogToConsole) {
			/* 368 */createConsoleAppender();
			/*     */} else {
			/* 370 */loggerObj.setAdditivity(false);
			/* 371 */removeConsoleAppender();
			/*     */}
		/* 373 */if (this.bLogToFile)
			/* 374 */createFileAppender(this.loggerName,
			/* 375 */(byte) this.nChangeLogFlag, this.nMaxFileSize,
			/* 376 */this.nMaxFileIndex);
		/*     */else {
			/* 378 */removeFileAppender();
			/*     */}
		/* 380 */if (this.bLogToSysLog)
			/* 381 */createSyslogAppender(this.syslogHost, 513,
			/* 382 */0);
		/*     */else
			/* 384 */removeSyslogAppender();
		/*     */}
	/*     */
}

/*
 * Location:
 * D:\chaulh\smartfoxv64\SmartFoxServer_2X\SFS2X\extensions\__lib__\card
 * -game-main.jar Qualified Name: com.cardgame.log.LogWriter JD-Core Version:
 * 0.6.0
 */