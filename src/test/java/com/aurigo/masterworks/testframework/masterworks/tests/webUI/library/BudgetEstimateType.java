package com.aurigo.masterworks.testframework.masterworks.tests.webUI.library;

import com.aurigo.masterworks.testframework.masterworks.tests.BaseTest;
import com.aurigo.masterworks.testframework.utilities.models.User;
import com.aurigo.masterworks.testframework.utilities.models.UserDataReader;
import com.aurigo.masterworks.testframework.webUI.common.SharedSteps;
import com.aurigo.masterworks.testframework.webUI.pages.LoginPage;
import org.testng.annotations.Test;

public class BudgetEstimateType extends BaseTest {

    @Test
    public void budgetManagementTypeLibraryPageValidation()
    {
        getPage(LoginPage.class).login();
    }

}
