package com.zltel.broadcast.wechat_pay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * xml工具
 * @author 张毅
 * @since jdk 1.8.0_191_b12
 * date: 2018.10.31
 */
public class Xml_Util {
	
	/**  
     * @Description：将请求参数转换为xml格式的string  
     * @param parameters 请求参数  
     * @return  
     */    
    public static String getRequestXml(SortedMap<String, String> parameters) {    
        StringBuffer sb = new StringBuffer();    
        sb.append("<xml>");    
        Iterator<Entry<String, String>> it = parameters.entrySet().iterator();    
        while (it.hasNext()) {    
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();    
            String k = (String) entry.getKey();    
            String v = (String) entry.getValue();    
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {    
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");    
            } else {    
                sb.append("<" + k + ">" + v + "</" + k + ">");    
            }    
        }    
        sb.append("</xml>");    
        return sb.toString();    
    }
    
	/**  
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。  
     * @param strxml  
     * @return  
     * @throws JDOMException  
     * @throws IOException  
     */    
    @SuppressWarnings("rawtypes")
	public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException {    
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");    

        if(null == strxml || "".equals(strxml)) {    
            return null;    
        }    

        Map<String, String> m = new HashMap<String, String>();    

        InputStream in = null;    
        try {
			in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List list = root.getChildren();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String k = e.getName();
				String v = "";
				List children = e.getChildren();
				if (children.isEmpty()) {
					v = e.getTextNormalize();
				} else {
					v = getChildrenText(children);
				}

				m.put(k, v);
			} 
		} finally {
			if (in != null) in.close();
		}  

        return m;    
    }    

    /**  
     * 获取子结点的xml  
     * @param children  
     * @return String  
     */    
    @SuppressWarnings("rawtypes")
	private static String getChildrenText(List children) {    
        StringBuffer sb = new StringBuffer();    
        if(!children.isEmpty()) {    
            Iterator it = children.iterator();    
            while(it.hasNext()) {    
                Element e = (Element) it.next();    
                String name = e.getName();    
                String value = e.getTextNormalize();    
                List list = e.getChildren();    
                sb.append("<" + name + ">");    
                if(!list.isEmpty()) {    
                    sb.append(getChildrenText(list));    
                }    
                sb.append(value);    
                sb.append("</" + name + ">");    
            }    
        }    

        return sb.toString();    
    } 
}
