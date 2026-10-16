package com.projectpear;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Checks that {@link AppLogger#logError(Exception)} appends the exception
 * to the Project PEAR log file.
 */
public class AppLoggerTest {
    @Test
    public void logErrorWritesExceptionToLogFile() throws Exception {
        String token = "pear-log-test-" + UUID.randomUUID();
        AppLogger.logError(new IllegalStateException(token));

        String localAppData = System.getenv("LOCALAPPDATA");
        Path dir = localAppData != null
            ? Path.of(localAppData, "Project PEAR")
            : Path.of(".");

        boolean found = false;
        try (Stream<Path> files = Files.list(dir)) {
            for (Path file : files.toList()) {
                String name = file.getFileName().toString();
                if (!name.startsWith("pear.log") || name.endsWith(".lck")) {
                    continue;
                }
                if (Files.readString(file).contains(token)) {
                    found = true;
                    break;
                }
            }
        }

        Assert.assertTrue("log file should contain " + token, found);
    }
}
