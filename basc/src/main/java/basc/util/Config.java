package basc.util;

import java.io.*;
import java.util.Properties;

public class Config {
    public static Properties getClientConfig(String path) throws IOException {
        Properties config = new Properties();
        InputStream is = new FileInputStream(path);
        config.load(is);

        return config;
    }
}
