package com.fly.pay.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class BeanUtil {
	public static String map2xml(Map<String,String> dataMap){
		StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<xml>");
        Set<String> objSet = dataMap.keySet();
        for (String key : objSet)
        {
            if (key == null)
            {
                continue;
            }
            strBuilder.append("\n");
            strBuilder.append("<").append(key.toString()).append("><![CDATA[");
            String value = dataMap.get(key);
            strBuilder.append(value);
            strBuilder.append("]]></").append(key.toString()).append(">\n");
        }
        strBuilder.append("</xml>");
        return strBuilder.toString();
	}
	
	public static String map2uri(Map<String,String> dataMap){
		StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("");
        Set<String> objSet = dataMap.keySet();
        for (String key : objSet)
        {
            if (key == null)
            {
                continue;
            }
            strBuilder.append(key.toString()).append("=");
            String value = dataMap.get(key);
            strBuilder.append(value).append("&");
        }
        String uri=strBuilder.toString();
        if(uri.length()==0){
        	return uri;
        }
        return uri.substring(0, uri.length()-1);
	}
	
	public static Map<String,String> xmltoMap(String xml) {  
        try {  
            Map<String,String> map = new HashMap<String,String>();  
            Document document = DocumentHelper.parseText(xml);  
            Element nodeElement = document.getRootElement();  
            List node = nodeElement.elements();  
            for (Iterator it = node.iterator(); it.hasNext();) {  
                Element elm = (Element) it.next();  
                map.put(elm.getName(), elm.getText());  
                elm = null;  
            }  
            node = null;  
            nodeElement = null;  
            document = null;  
            return map;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
}
