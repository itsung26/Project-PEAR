package com.projectpear;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Runs Windows {@code cmd.exe} commands and captures their combined output.
 *
 * <p>Used for elevation checks and firewall policy changes. Callers should
 * typically be running with administrator privileges for firewall commands
 * to succeed.
 *
 * <p>Example:
 * <pre>{@code
 * String status = WindowsCommandLine.run(WindowsCommandLine.ELEVATED_QUERY).trim();
 * boolean elevated = status.equals("Elevated");
 * }</pre>
 */
public final class WindowsCommandLine {
    /**
     * Prints {@code Elevated} or {@code Not elevated} based on whether
     * {@code net session} succeeds (requires admin).
     */
    public static final String ELEVATED_QUERY =
        "net session >nul 2>&1 && echo Elevated || echo Not elevated";

    /**
     * Blocks inbound and outbound traffic on all Windows Firewall profiles.
     */
    public static final String FIREWALL_BLOCK_ALL =
        "netsh advfirewall set allprofiles firewallpolicy blockinbound,blockoutbound";

    /**
     * Restores typical policy: block inbound, allow outbound, on all profiles.
     */
    public static final String FIREWALL_ALLOW_ALL =
        "netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound";

    private WindowsCommandLine() {
        // utility class
    }

    /**
     * Executes {@code command} via {@code cmd.exe /c} and returns stdout
     * (and waits for the process to exit).
     *
     * @param command the command string passed to {@code cmd.exe /c}
     * @return process output, or an empty string if an error occurs
     */
    public static String run(String command) {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
        try {
            Process process = pb.start();

            String output;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append(System.lineSeparator());
                }
                output = sb.toString();
            }
            process.waitFor();
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
