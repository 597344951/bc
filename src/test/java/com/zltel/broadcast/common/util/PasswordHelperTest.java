package com.zltel.broadcast.common.util;


import org.junit.Test;

public class PasswordHelperTest {
 

    @Test
    public void testEncryptPassword() throws Exception {
        String psin = "develop";
        String salt = "123456";
        String result = PasswordHelper.encryptPassword(psin, salt);
        System.out.println(result);
    }
}
