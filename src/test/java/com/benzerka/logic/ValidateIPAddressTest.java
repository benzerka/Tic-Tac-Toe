package com.benzerka.logic;

import org.junit.Assert;
import org.junit.Test;

public class ValidateIPAddressTest {
    @Test
    public void shouldReturnFalse(){
        // given
        ValidateIPAddress validateIPAddress = new ValidateIPAddress();
        // then
        Assert.assertFalse(validateIPAddress.validate("123.123.123.123.123"));
        Assert.assertFalse(validateIPAddress.validate("256.12.12.12"));
        Assert.assertFalse(validateIPAddress.validate("256.256.12.12"));
        Assert.assertFalse(validateIPAddress.validate("256.12.256.12"));
        Assert.assertFalse(validateIPAddress.validate("256.12.12.256"));
        Assert.assertFalse(validateIPAddress.validate("256.12.12"));
        Assert.assertFalse(validateIPAddress.validate("a.b.c.d"));
    }

    @Test
    public void shouldReturnTrue(){
        // given
        ValidateIPAddress validateIPAddress = new ValidateIPAddress();
        // then
        Assert.assertTrue(validateIPAddress.validate("127.0.0.1"));
        Assert.assertTrue(validateIPAddress.validate("255.255.0.1"));
        Assert.assertTrue(validateIPAddress.validate("7.26.64.34"));
    }

}