package framework;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class DirectoryManager {
    private DirectoryManager() {}

    public static void initializeFrameworkDirectories()
    {
        createDirectories(FrameworkPaths.getExecutionDirectory());
        createDirectories(FrameworkPaths.getScreenshotDirectory());
        createDirectories(FrameworkPaths.getLogDirectory());
        createDirectories(FrameworkPaths.getDownloadDirectory());
    }

    private static void createDirectories(String directory)
    {
       Path path = Paths.get(directory);
       try
       {
           Files.createDirectories(path);
       } catch (IOException e) {
           throw new RuntimeException(
                   "Unable to create framework directory : " + directory, e);
       }
    }
}
