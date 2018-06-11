package com.zltel.broadcast.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/** 颜色计算工具 **/
public class ColorUtil {
    public String rgbToHex(int[] rgb) {
        StringBuffer sb = new StringBuffer();
        IntStream.of(rgb).forEach(i->{
            String hex = Integer.toHexString(i);
            IntStream.range(hex.length(), 2).forEach(f->{
                sb.append("0");
            });
            sb.append(hex);
        });
        
        return "#" +  sb.toString();
    }

    public int[] hexToRgb(String hex) {
        List<Integer> list = new ArrayList<>();
        int[] ret = new int[3];
        int idx = 0;
        for (int i = 1; i < 7; i += 2) {
            int r = Integer.parseInt(hex.substring(i, i + 2),16);  
            ret[idx] = r;
            idx ++;
        }
        return ret;
    }

    public String gradient(String startColor, int step, int opera) {
        // 将hex转换为rgb
        int[] sColor = this.hexToRgb(startColor);
        // 计算R\G\B每一步的差值
        int[] ncolor = IntStream.of(sColor).map(i -> i +(step * opera)).toArray();
        return this.rgbToHex(ncolor);
    }
    
    
}
