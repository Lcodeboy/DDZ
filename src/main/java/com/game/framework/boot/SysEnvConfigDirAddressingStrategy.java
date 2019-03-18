package com.game.framework.boot;

import java.io.File;

public class SysEnvConfigDirAddressingStrategy implements ConfigDirAddressingStrategy {
    public File getConfigDir() throws Exception {
        String value = System.getenv("JAVA_HOME");
        System.out.println(value);
        File file = new File(value);
        if (file.isDirectory() && file.exists()){
            return file;
        }
        return null;
    }

    public static void main(String[] args) {
        SysEnvConfigDirAddressingStrategy sys = new SysEnvConfigDirAddressingStrategy();
        try {
           File file = sys.getConfigDir();
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
