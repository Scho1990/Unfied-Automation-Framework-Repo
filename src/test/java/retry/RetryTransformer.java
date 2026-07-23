package retry;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryTransformer implements IAnnotationTransformer {
    private static final Logger logger = LogManager.getLogger(RetryTransformer.class);
    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        logger.info("Method : {}", testMethod.getName());
        logger.info("Existing Retry Analyzer : {}", annotation.getRetryAnalyzerClass());
        logger.info("Setting RetryAnalyzer");
        annotation.setRetryAnalyzer(RetryAnalyzer.class);

    }
}
