package com.training.base;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LT extends BaseTest	{
	@Test
    public void verifyLogin() {
        System.out.println("Executing VerifyLogin Test");
        Assert.assertTrue(true, "Placeholder assertion."); // Replace with actual assertion
    }
}
