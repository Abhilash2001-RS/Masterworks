package com.aurigo.masterworks.testframework.utilities.listeners;

import com.aurigo.masterworks.testframework.utilities.helper.EnvironmentHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;

public class RetryTest {

    private int retryCount = 0;
    private int maxCount = Integer.parseInt(EnvironmentHelper.getPropertyValue("failTestRetryCount"));
    private static final Logger logger = LogManager.getLogger(RetryTest.class);

    public boolean retry(ITestResult result){
        if(retryCount<maxCount){
            logger.info("Retrying!.." + result.getMethod().getMethodName() + "with status "
            + getResultStatusName(result.getStatus()) + "Attempt No. "+ (retryCount +1) );
            retryCount++;
            return true;
        }
        return false;
    }


    public String getResultStatusName(int status){
        String resultName = null;
        if(status == 1)
            resultName = "Success";
        if(status == 2)
            resultName = "Failure";
        if(status==3)
            resultName ="Skip";

        return resultName;
    }
}
