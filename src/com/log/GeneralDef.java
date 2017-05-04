package com.log;
/*     */ 
/*     */ public class GeneralDef
/*     */ {
/* 177 */   public static final String copyright = "# Copyright (c) 2006 Elcom Company JSC " + OS.lineSeperator + 
/* 173 */     "# This product includes software developed by Elcom by whom copyright " + 
/* 175 */     OS.lineSeperator + 
/* 177 */     "# and know-how are retained, all rights reserved.";
/*     */   public static final String projectName = "# ProjectName";
/*     */   public static final String version = "#1.0";
/*     */ 
/*     */   public static class LOG_LEVEL
/*     */   {
/*     */     public static final byte LOG_ALL = 0;
/*     */     public static final byte LOG_DEBUG = 1;
/*     */     public static final byte LOG_INFO = 2;
/*     */     public static final byte LOG_WARNING = 3;
/*     */     public static final byte LOG_ERROR = 4;
/*     */     public static final byte LOG_FATAL = 5;
/*     */     public static final byte LOG_OFF = 6;
/*     */   }
/*     */ 
/*     */   public static class OS
/*     */   {
/*     */     public static final byte PLATFORM_SOLARIS = 1;
/*     */     public static final byte PLATFORM_LINUX = 2;
/*     */     public static final byte PLATFORM_WINDOW = 3;
/*     */     public static final String LINE_SEPERATOR_WINDOW = "\r\n";
/*     */     public static final String LINE_SEPERATOR_UNIX = "\n";
/*     */     public static final String FILE_SEPERATOR_WINDOW = "\\";
/*     */     public static final String FILE_SEPERATOR_UNIX = "/";
/*     */     public static final String timezone = "GMT+7";
/*  39 */     public static String csOSName = null;
/*     */ 
/*  41 */     public static byte nPlatform = 1;
/*     */ 
/*  43 */     public static String lineSeperator = "";
/*     */ 
/*  45 */     public static String fileSeperator = "";
/*     */ 
/*     */     static
/*     */     {
/*  59 */       csOSName = System.getProperty("os.name");
/*     */ 
/*  61 */       csOSName = csOSName.toLowerCase();
/*     */ 
/*  63 */       if (csOSName.indexOf("linux") >= 0)
/*     */       {
/*  65 */         nPlatform = 2;
/*     */ 
/*  67 */         lineSeperator = "\n";
/*     */ 
/*  69 */         fileSeperator = "/";
/*     */       }
/*  73 */       else if (csOSName.indexOf("windows") >= 0)
/*     */       {
/*  75 */         nPlatform = 3;
/*     */ 
/*  77 */         lineSeperator = "\r\n";
/*     */ 
/*  79 */         fileSeperator = "\\";
/*     */       }
/*  83 */       else if (csOSName.indexOf("solaris") >= 0)
/*     */       {
/*  85 */         nPlatform = 1;
/*     */ 
/*  87 */         lineSeperator = "\n";
/*     */ 
/*  89 */         fileSeperator = "/";
/*     */       }
/*  93 */       else if (csOSName.indexOf("sun") >= 0)
/*     */       {
/*  95 */         nPlatform = 1;
/*     */ 
/*  97 */         lineSeperator = "\n";
/*     */ 
/*  99 */         fileSeperator = "/";
/*     */       }
/*     */       else
/*     */       {
/* 107 */         fileSeperator = "?";
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\chaulh\smartfoxv64\SmartFoxServer_2X\SFS2X\extensions\__lib__\card-game-main.jar
 * Qualified Name:     com.cardgame.log.GeneralDef
 * JD-Core Version:    0.6.0
 */