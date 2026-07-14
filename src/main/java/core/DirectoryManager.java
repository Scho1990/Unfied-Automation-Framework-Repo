package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class DirectoryManager {
    private DirectoryManager() {}

    public static void initializeCoreDirectories()
    {
        createDirectories(FrameworkPaths.getExecutionDirectory());
        createDirectories(FrameworkPaths.getScreenshotDirectory());
        createDirectories(FrameworkPaths.getLogDirectory());
        createDirectories(FrameworkPaths.getDownloadDirectory());
    }

    private static void createDirectories(Path directory)
    {
       try
       {
           Files.createDirectories(directory);
       } catch (IOException e) {
           throw new RuntimeException(
                   "Unable to create framework directory : " + directory, e);
       }
    }
}
