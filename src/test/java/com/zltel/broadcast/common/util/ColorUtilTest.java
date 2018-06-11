package com.zltel.broadcast.common.util;


import javax.annotation.Generated;

import org.junit.Test;

@Generated(value = "org.junit-tools-1.0.6")
public class ColorUtilTest {
    
    @Test
    public void testGradient() throws Exception {
        ColorUtil testSubject = new ColorUtil();
        String startColor = "#0748A3";
        int step = 7;
        int opera = 1;
        String endColor = testSubject.gradient(startColor, step, opera);
        System.out.println("end Color:"+endColor);
    }
}
