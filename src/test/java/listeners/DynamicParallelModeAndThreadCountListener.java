package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;

public class DynamicParallelModeAndThreadCountListener implements IAlterSuiteListener {
    public static final Logger logger = LogManager.getLogger(DynamicParallelModeAndThreadCountListener.class);
    @Override
    public void alter(List<XmlSuite> suites){
         int threadCount = Integer.parseInt(System.getProperty("threadCount","3"));
         String parallelMode = System.getProperty("parallelMode","methods");
         for (XmlSuite suite : suites){
             suite.setThreadCount(threadCount);
             suite.setParallel(XmlSuite.ParallelMode.getValidParallel(parallelMode));
             logger.info("Parallel Mode          : {}", parallelMode);
             logger.info("Parallel Thread count  : {}", threadCount);
         }
    }
}
