package com.projectpear;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class WindowsCommandLine {
    public static final String ELEVATED_QUERY = "net session >nul 2>&1 && echo Elevated || echo Not elevated";
    public static final String FIREWALL_BLOCK_ALL = "netsh advfirewall set allprofiles firewallpolicy blockinbound,blockoutbound";
    public static final String FIREWALL_ALLOW_ALL = "netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound";

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
