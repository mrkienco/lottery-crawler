package com.log;

/*    */
/*    */import java.io.File;
/*    */
import java.io.IOException;
/*    */
import java.text.SimpleDateFormat;
/*    */
import java.util.Date;
/*    */
import org.apache.log4j.FileAppender;
/*    */
import org.apache.log4j.Layout;
/*    */
import org.apache.log4j.MDC;
/*    */
import org.apache.log4j.spi.ErrorHandler;
/*    */
import org.apache.log4j.spi.LoggingEvent;

/*    */
/*    */public class MyAppender extends FileAppender
/*    */{
	/*    */private static final String DOT = ".";
	/*    */private static final String HIPHEN = "-";
	/*    */private static final String ORIG_LOG_FILE_NAME = "OrginalLogFileName";
	/*    */private Object lastEvent;
	/* 30 */final String dir = System.getProperty("user.dir");

	/*    */
	/*    */public MyAppender()
	/*    */{
		/*    */}

	/*    */
	/*    */public MyAppender(Layout layout, String fileName, boolean append,
			boolean bufferedIO, int bufferSize) throws IOException {
		/* 37 */super(layout, fileName, append, bufferedIO, bufferSize);
		/*    */}

	/*    */
	/*    */public MyAppender(Layout layout, String fileName, boolean append)
			throws IOException
	/*    */{
		/* 42 */super(layout, fileName, append);
		/*    */}

	/*    */
	/*    */public MyAppender(Layout layout, String fileName) throws IOException {
		/* 46 */super(layout, "init_file");
		/*    */}

	/*    */
	/*    */public void activateOptions()
	/*    */{
		/* 53 */MDC.put("OrginalLogFileName", this.fileName);
		/* 54 */super.activateOptions();
		/*    */}

	public void append(LoggingEvent event) {
		try {

			Date currentDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-H");
			String dateForm = dateFormat.format(currentDate);
			String message = (String) event.getMessage();
			String[] messagePart = message.split("@");
			String[] logNames = dateForm.split("-");
			StringBuilder logFileName = new StringBuilder();
			String category = "Lobby";
			if ((messagePart != null)) {
				if (messagePart.length == 3) {
					category = messagePart[1];
					logFileName.append(this.dir).append(File.separator);
					logFileName.append("logs").append(File.separator)
							.append(category).append(File.separator)
							.append(logNames[0]).append(File.separator)
							.append(logNames[1]).append(File.separator)
							.append(logNames[2]);
				} else if (messagePart.length == 4) {
					category = messagePart[2];
					String location = messagePart[1];
					logFileName.append(this.dir).append(File.separator);
					logFileName.append("logs").append(File.separator)
							.append(category).append(File.separator)
							.append(location).append(File.separator)
							.append(logNames[0]).append(File.separator)
							.append(logNames[1]).append(File.separator)
							.append(logNames[2]);
				}
			}

			logFileName.append(File.separator).append("log-").append(dateForm);
			setFile(logFileName.toString(), this.fileAppend, this.bufferedIO,
					this.bufferSize);
		} catch (IOException ie) {
			this.errorHandler.error(
					"Error occured while setting file for the log level "
							+ event.getLevel(), ie, 4);
		}
		super.append(event);
	}
	// public void append(LoggingEvent event) {
	// try {
	// Date currentDate = new Date();
	// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-H");
	// String dateForm = dateFormat.format(currentDate);
	// String message = (String) event.getMessage();
	// String[] messagePart = message.split("%");
	//
	// String roomName = "Lobby";
	// if ((messagePart != null) && (messagePart.length >= 3)) {
	// roomName = messagePart[1];
	// }
	//
	// String[] logNames = dateForm.split("-");
	// StringBuilder logFileName = new StringBuilder();
	// logFileName.append(this.dir).append(File.separator);
	// logFileName.append("CardGameLog").append(File.separator)
	// .append(logNames[0]).append(File.separator)
	// .append(logNames[1]).append(File.separator)
	// .append(logNames[2]).append(File.separator)
	// .append(roomName);
	//
	// logFileName.append(File.separator).append("millionaire-")
	// .append(dateForm);
	// setFile(logFileName.toString(), this.fileAppend, this.bufferedIO,
	// this.bufferSize);
	// } catch (IOException ie) {
	// this.errorHandler.error(
	// "Error occured while setting file for the log level "
	// + event.getLevel(), ie, 4);
	// }
	// super.append(event);
	// }
}

/*
 * Location:
 * D:\chaulh\smartfoxv64\SmartFoxServer_2X\SFS2X\extensions\__lib__\card
 * -game-main.jar Qualified Name: com.cardgame.log.MyAppender JD-Core Version:
 * 0.6.0
 */