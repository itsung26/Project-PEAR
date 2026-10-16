package com.projectpear;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppLogger {
    private static final Logger logger = Logger.getLogger(AppLogger.class.getName());

    private static Logger getLogger() {
        return logger;
    }

    /**
     * Appends {@code e} and its stack trace to the log file.
     *
     * <p>Opens a new file handler for this call, writes one record, and closes
     * it. The log is {@code %LOCALAPPDATA%\Project PEAR\pear.log}.
     *
     * @param e the error to record
     */
    public static void logError(Exception e) {
        FileHandler handler = null;
        try {
            String localAppData = System.getenv("LOCALAPPDATA");
            Path dir = localAppData != null
                ? Path.of(localAppData, "Project PEAR")
                : Path.of(".");
            Files.createDirectories(dir);
            handler = new FileHandler(
                dir.resolve("pear.log").toString(),
                256_000,
                3,
                true
            );
            handler.setFormatter(new SimpleFormatter());

            LogRecord record = new LogRecord(Level.SEVERE, e.toString());
            record.setLoggerName(logger.getName());
            record.setThrown(e);
            handler.publish(record);
            handler.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to open log file", ex);
            logger.log(Level.SEVERE, e.toString(), e);
        } finally {
            if (handler != null) {
                handler.close();
            }
        }
    }
}
