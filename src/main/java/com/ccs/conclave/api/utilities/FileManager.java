package com.ccs.conclave.api.utilities;

import java.io.File;

public class FileManager {
    private static String fs = File.separator;

    public static String getJsonData(String fileName) {
        String configPath = String.format("%ssrc%smain%sresources%stestdata%s", fs, fs, fs, fs, fs);
        String filePath = System.getProperty("user.dir") + configPath;
        return filePath + fileName;
    }
}
