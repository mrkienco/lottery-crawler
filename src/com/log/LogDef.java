package com.log;
/*    */ 
/*    */ public class LogDef
/*    */ {
/*    */   public static class FILE
/*    */   {
/*    */     public static final byte CHANGE_ON_SZIE = 1;
/*    */     public static final byte CHANGE_ON_INDEX = 2;
/*    */     public static final byte CHANGE_ON_DATE = 3;
/*    */     public static final int MAX_FILE_SIZE = 10485760;
/* 19 */     public static int MAX_FILE_BACKUP_INDEX = 1000;
/*    */   }
/*    */ 
/*    */   public static class LOG_LEVEL
/*    */   {
/*    */     public static final byte LOG_ALL = 0;
/*    */     public static final byte LOG_DEBUG = 1;
/*    */     public static final byte LOG_INFO = 2;
/*    */     public static final byte LOG_WARNING = 3;
/*    */     public static final byte LOG_ERROR = 4;
/*    */     public static final byte LOG_FATAL = 5;
/*    */     public static final byte LOG_OFF = 6;
/*    */   }
/*    */ 
/*    */   public static class SYSLOG
/*    */   {
/*    */     public static final int DFLT_PORT = 513;
/*    */   }
/*    */ }

/* Location:           D:\chaulh\smartfoxv64\SmartFoxServer_2X\SFS2X\extensions\__lib__\card-game-main.jar
 * Qualified Name:     com.cardgame.log.LogDef
 * JD-Core Version:    0.6.0
 */