package epam.com.tests;

import epam.com.core.RestApiClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseApiTest {

    protected static final Logger logger = LogManager.getLogger(BaseApiTest.class);
    protected RestApiClient restApiClient = new RestApiClient();

    @BeforeMethod
    public void beforeMethod(Method method) {
        logger.info("Checking \"" + method.getName() + "\" functionality");
        logger.info("Thread #" + Thread.currentThread().getId());
    }
}
