package core;

import exceptions.FrameworkException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class DirectoryManager {
    private DirectoryManager() {}

    public static void initializeExecutionDirectories()
    {
        createDirectory(FrameworkPaths.getExecutionDirectory());
        createDirectory(FrameworkPaths.getScreenshotDirectory());
        createDirectory(FrameworkPaths.getLogDirectory());
        createDirectory(FrameworkPaths.getDownloadDirectory());
    }

    private static void createDirectory(Path directory)
    {
       try
       {
           Files.createDirectories(directory);
       } catch (IOException e) {
           throw new FrameworkException(
                   "Unable to create framework directory : %s' ".formatted(directory), e);
       }
    }
}
