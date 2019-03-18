package com.game.framework.boot;

import java.io.File;

public class UserConfigDirAddressingStrategy implements ConfigDirAddressingStrategy {
    public File getConfigDir() throws Exception {
        return new File(System.getProperty("user.dir"));
    }
}
