package application;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Utility {
    public static String joinPath(String directoryName, String fileName) {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        return Paths.get(currentPath.toString(), directoryName, fileName).toString();
    }
}
