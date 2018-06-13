package com.zltel.broadcast.common.util;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.BroadcastApplicationTests;

@Generated(value = "org.junit-tools-1.0.6")
public class FileContentTypeUtilTest extends BaseTests {

    @Test
    public void testGetContentType() throws Exception {
        String fe = "";
        String result;
        
        fe = ".jpg";
        result = FileContentTypeUtil.getContentType(fe);
        logout.info("{} -> {}",fe,result);
        
        fe = ".png";
        result = FileContentTypeUtil.getContentType(fe);
        logout.info("{} -> {}",fe,result);
        
        fe = ".gif";
        result = FileContentTypeUtil.getContentType(fe);
        logout.info("{} -> {}",fe,result);
        
        fe = "a.jpeg";
        result = FileContentTypeUtil.getContentType(fe);
        logout.info("{} -> {}",fe,result);
        
        fe = ".mp4";
        result = FileContentTypeUtil.getContentType(fe);
        logout.info("{} -> {}",fe,result);
    }
}
