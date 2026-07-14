package core;

public final class FrameworkBootstrap {

    public static  void initialize(){
        DirectoryManager.initializeExecutionDirectories();
        System.getProperty(
                "framework.log.directory",
                FrameworkPaths
                        .getLogDirectory()
                        .toString());
    }
}
